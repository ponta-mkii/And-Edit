package co.jp.and_edit;
//2015/4/28/update
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class Edit_main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_main);

		Button btn = (Button)findViewById(R.id.New);
			btn.setOnClickListener(new View.OnClickListener() {
				@Override
	      		public void onClick(View v) {
					// インテントのインスタンス生成
					Intent intent = new Intent(Edit_main.this, SubMain.class);
					// 次画面のアクティビティ起動
					startActivity(intent);
				}
			});

	}

	//ボタンのクリックイベントが発生した時
	public void ClickEvent(View v){
	  //IDごとに処理を分ける
	  switch (v.getId()) {
	  //新規作成ボタンの時
	  case R.id.New:
	    //明示的インテント
	    Intent intent = new Intent( Edit_main.this, SubMain.class );
	    //画面遷移
	    startActivity( intent );

	   default:
	     break;

      case R.id.Open:
          try {
              String fileName = "sample.txt";
              SubMain.loadTextSDCard(fileName);
          }catch (Exception e){

          }

         break;
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_main, menu);
		return true;
	}


}
