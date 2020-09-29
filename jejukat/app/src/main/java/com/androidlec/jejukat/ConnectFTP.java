package com.androidlec.jejukat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ConnectFTP extends AsyncTask<Integer, String, Boolean> {

        private final String TAG = "Connect FTP";
        public FTPClient mFTPClient = null;

        Context context;
        String host;
        String username;
        String password;
        int port;
        ArrayList<Uri> filePath;

        CustomProgressDialog cProgressDialog;

        public ConnectFTP(Context context, String host, String username, String password, int port, ArrayList<Uri> URIS) {
            this.context = context;
            this.host = host;
            this.username = username;
            this.password = password;
            this.port = port;
            this.filePath = URIS;
            mFTPClient = new FTPClient();
//            Log.v("filepath",filePath.toString());
        }

    @Override
    protected void onPreExecute() {
        cProgressDialog = new CustomProgressDialog( context );
        cProgressDialog.setCancelable( false );
        cProgressDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        cProgressDialog.show();

        // 업로드중 다이어로그 메시지 변경
        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            int count = 0;
            public void run() {
                count++;
                if(count == 1)
                    cProgressDialog.setDialogMessage("나만의 장소 등록중..");
                else if(count == 2)
                    cProgressDialog.setDialogMessage("업로드 중..");
                mHandler.postDelayed(this, 1800);
            }
        }, 1200);


    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        cProgressDialog.dismiss();
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {

        // FTP 접속 체크
        boolean status = false;

        // FTP 접속 시
        if (status = ftpConnect(host, username, password, port)) {
            String currentPath = ftpGetDirectory() ;
                Log.v("filepath",filePath.toString());
            STATICDATA.FILENAMES = new ArrayList<String>();

            for(int step=0; step<filePath.size(); step++) {
                int index = filePath.get( step ).toString().lastIndexOf("/"); // 마지막 파일 몇번째인지 찾고
//                    Log.v(TAG, "intimg : " + index);
                String imgName = filePath.get( step ).toString().substring( index + 1); // 그 다음부터(+1) 이미지 이름 잘라서
                String replaceimgName = imgName.replaceAll("%","");

                // 업로드시간으로 파일이름정하기
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                Date curDate = new Date( System.currentTimeMillis());
                String filename = formatter.format(curDate);
                String Imagefilename = replaceimgName + STATICDATA.SEQ_USER + filename + Integer.toString(step+1);
                STATICDATA.FILENAMES.add("119.207.169.213:8080/ftp/"+Imagefilename);

                // 파일 업로드시 파일이름+올린시간으로 설정 함
                if (ftpUploadFile(filePath.get( step ), Imagefilename + ".jpg", currentPath)) {
//                        Log.v("ConnectFTP", "Success");
                }
            }
        }

        if (status == true) {
            ftpDisconnect();
            Log.v("STATICDATA", STATICDATA.FILENAMES.toString());
            STATICDATA.FTPUPLOAD = 1;
            STATICDATA2.FTPUPLOAD = 1;
        }
        return true;
    }

    public boolean ftpConnect(String host, String username, String password, int port) {
        boolean result = false;

        try {
            mFTPClient.connect(host, port);
            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                result = mFTPClient.login(username, password);
                mFTPClient.enterLocalPassiveMode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean ftpDisconnect() {
        boolean result = false;

        try {
            mFTPClient.logout();
            mFTPClient.disconnect();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String ftpGetDirectory() {
        String directory = null;
        try {
            directory = mFTPClient.printWorkingDirectory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory;
    }

    public boolean ftpChangeDirectory(String directory) {
        try {
            mFTPClient.changeWorkingDirectory(directory);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //                           파일 경로      저장할 파일 이름       저장할 FTP 폴더 경로
    public boolean ftpUploadFile(Uri file, String desFileName, String desDriectroy) {
        boolean result = false;
        try {
            InputStream fis = context.getContentResolver().openInputStream(file);
            if (ftpChangeDirectory(desDriectroy)) {
                // FTP File 타입 설정
                mFTPClient.setFileType( FTPClient.BINARY_FILE_TYPE);
                result = mFTPClient.storeFile(desFileName, fis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



        public String[] ftpGetFileList(String directory) {
            String[] fileList = null;
            int i = 0;
            try {
                FTPFile[] ftpFiles = mFTPClient.listFiles(directory);
                fileList = new String[ftpFiles.length];
                for (FTPFile file : ftpFiles) {
                    String fileName = file.getName();

                    if (file.isFile()) {
                        fileList[i] = "(File)" + fileName;
                    } else {
                        fileList[i] = "(Directory)" + fileName;
                    }
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return fileList;
        }

        public boolean ftpCreateDirectory(String directory) {
            boolean result = false;
            try {
                result = mFTPClient.makeDirectory(directory);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        public boolean ftpDeleteDirectory(String directory) {
            boolean result = false;
            try {
                result = mFTPClient.removeDirectory(directory);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        public boolean ftpDeleteFile(String file) {
            boolean result = false;
            try {
                result = mFTPClient.deleteFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        public boolean ftpRenameFile(String from, String to) {
            boolean result = false;
            try {
                result = mFTPClient.rename(from, to);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }


    public boolean ftpDownloadFile(String srcFilePath, String desFilePath) {
        boolean result = false;
        try {
            mFTPClient.setFileType( FTP.BINARY_FILE_TYPE);
            mFTPClient.setFileTransferMode( FTP.BINARY_FILE_TYPE);

            FileOutputStream fos = new FileOutputStream(desFilePath);
            result = mFTPClient.retrieveFile(srcFilePath, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }






}
