package co.jp.and_edit;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class SubMain extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  // 画面の XML を指定する
	  setContentView(R.layout.activity_sub_main);
	}

	//ボタンのクリックイベントが発生した時
	public void onClick(View v){
		//IDごとに処理を分ける
		switch (v.getId()) {
		//保存ボタンの時
		case R.id.btn_Save:
			//処理は後ほど記載します。

            String fileName = "sample.txt";  // "mnt/sdcard/sample.txt" or "sdcard/sample.txt" になる(端末による)
            String text ="aaa";
            try{
            saveTextSDCard(fileName,text);
            } catch (Exception e) {
            }

        //キャンセルボタンの時
		case R.id.btn_Cancel:
			//画面を閉じる
			finish();

		default:
			break;
		}
	}

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}

/*-----------------------------------------------------------------------------------------------------------*/
    //設定値
    private static final String DEFAULT_ENCORDING = "UTF-8";//デフォルトのエンコード
    private static final int DEFAULT_READ_LENGTH = 8192;      //一度に読み込むバッファサイズ


    //入力ストリームから読み込み、バイト配列で返す(汎用)
    public static final byte[] readStream(InputStream inputStream, int readLength) throws IOException {
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream(readLength); //一時バッファのように使う
        final byte[] bytes = new byte[readLength];    //read() 毎に読み込むバッファ
        final BufferedInputStream bis = new BufferedInputStream(inputStream, readLength);

        try {
            int len = 0;
            while ((len = bis.read(bytes, 0, readLength)) > 0) {
                byteStream.write(bytes, 0, len);    //ストリームバッファに溜め込む
            }
            return byteStream.toByteArray();    //byte[] に変換

        } finally {
            try {
                byteStream.reset();     //すべてのデータを破棄
                bis.close();            //ストリームを閉じる
            } catch (Exception e) {
                //IOException
            }
        }
    }

    //出力ストリームにてテキストを保存(汎用)
    public static final void saveText(OutputStream outputStream, String text, String charsetName)
            throws IOException, UnsupportedEncodingException {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(outputStream);
            bos.write(text.getBytes(charsetName));  //すべての byte[] を取得
            bos.flush();
        } finally {
            try {
                bos.close();
            } catch (Exception e) {
                //IOException
            }
        }
    }

    //ストリームから読み込み、テキストエンコードして返す
    public static final String loadText(InputStream inputStream, String charsetName)
            throws IOException, UnsupportedEncodingException {
        return new String(readStream(inputStream, DEFAULT_READ_LENGTH), charsetName);
    }


    // SDCard のマウント状態のチェックする(Android 用)
    public static final boolean isMountSDCard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;   //マウントされている
        } else {
            return false;  //マウントされていない
        }
    }

    // SDCard のルートディレクトリを取得(Android 用)
    public static final File getSDCardDir() {
        return Environment.getExternalStorageDirectory();
    }

    // SDCard 内の絶対パスに変換(Android 用)
    public static final String toSDCardAbsolutePath(String fileName) {
        return getSDCardDir().getAbsolutePath() + File.separator + fileName;
    }


// SDCard から、テキストファイルを読み込む(Android 用)
//(ex) String fileName = "sample.txt";  // "mnt/sdcard/sample.txt" or "sdcard/sample.txt" になる(端末による)
//     String text = loadTextSDCard(fileName);
    public static final String loadTextSDCard(String fileName) throws IOException {
        if (!isMountSDCard()) {  //マウント状態のチェック
            throw new IOException("No Mount");
        }
        final String absPath = toSDCardAbsolutePath(fileName);  //SDCard 内の絶対パスに変換
        InputStream is = new FileInputStream(absPath);
        return loadText(is, DEFAULT_ENCORDING);
    }


//SDCard に、テキストファイルを保存する(Android 用)
// (ex) String fileName = "sample.txt";  // "mnt/sdcard/sample.txt" or "sdcard/sample.txt" になる(端末による)
//      String text = "保存するテキスト";
//      saveTextSDCard(fileName, text);
    public static final void saveTextSDCard(String fileName, String text) throws IOException {
        if (!isMountSDCard()) {
            throw new IOException("No Mount");
        }
        final String absPath = toSDCardAbsolutePath(fileName);  //SDCard 内の絶対パスに変換
        OutputStream os = new FileOutputStream(absPath);
        saveText(os, text, DEFAULT_ENCORDING);
    }

}
