package com.guhuidougeiniyangle;
//导入TTS的包
import android.speech.tts.TextToSpeech; 
//导入监听包
import android.speech.tts.TextToSpeech.OnInitListener;
import java.util.*;
import android.widget.CompoundButton.*;
import android.graphics.*;
import android.view.inputmethod.*;
import android.content.*;
import android.net.*;
import java.io.*;
import android.content.pm.*;
import android.media.*;
import android.content.res.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*; 

public class FileList extends Activity 
{


	Context mContext;
	File file;
	String root = "/sdcard/骨灰扬了夫/文本文件列表/";

	ListView lv;

	private AlertDialog ad;

	private ArrayList<String> data;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelist);
		mContext = getApplicationContext();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		lv = (ListView)findViewById(R.id.filelistListView1);

		file = new File(root);
		f5();

		//单击函数
		lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					String filename = data.get(p3); 

					Intent intent=new Intent(FileList.this, TextList.class);
					//可以把要传递的参数放到一个bundle里传递过去，bundle可以看做一个特殊的map.
					Bundle bundle=new Bundle();
					bundle.putString("result", filename);
					intent.putExtras(bundle);

					startActivity(intent);

				}
			});

	
			
		lv.setOnItemLongClickListener(new OnItemLongClickListener(){

				private File file1;

				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					String filename = data.get(p3); 
					String filepath = root + filename;
					
					file1 = new File(filepath);
					
					
					new AlertDialog.Builder(FileList.this)
						.setTitle("提示")
						.setMessage("是否删除"+data.get(p3)+"?")
						.setPositiveButton("取消",null) 
						.setNeutralButton("确认",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								if(file1.delete()){
									Toast.makeText(getApplicationContext(),"删除成功",1).show();
								}
								f5();
							}
						})
						.show();
				
					return true;
				}
				
				
			});
	}

	private void f5()
	{
		String dat[] = file.list();

		data = new ArrayList();
		for (String a :dat)
		{

			if (a.endsWith(".gh"))
			{
				data.add(a);
			}

		}


		ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
		lv.setAdapter(ad);

		setTitle("文件数量 ： " + data.size());

		// TODO: Implement this method
	}





	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle presses on the action bar items


		switch (item.getItemId())
		{
			case R.id.item1:
				final LinearLayout ll = new LinearLayout(this);
				final TextView tv1 = new TextView(this);
				final TextView tv2 = new TextView(this);

				final Button bu1 = new Button(this);
				final Button bu2 = new Button(this);
				final Button bu3 = new Button(this);
				
				bu1.setText("生成");
				bu2.setText("关闭");
				bu3.setText("向后添加&");
			
				
				
				tv1.setText("标题");
				tv2.setText("喊话内容,多个用 \"&\" 隔开");
				final EditText ed1 = new EditText(this);
				final EditText ed2 = new EditText(this);
				ed1.setHint("保存的文件名");
				ed2.setHint("例子 : 哈喽&你好&再见");
				ed2.setHeight(300);
				ed2.setGravity(Gravity.TOP);
				ll.setOrientation(LinearLayout.VERTICAL);
				ll.addView(tv1);
				ll.addView(ed1);
				ll.addView(tv2);
				ll.addView(ed2);
				ll.addView(bu3);
				ll.addView(bu1);ll.addView(bu2);
				ad = new AlertDialog.Builder(this).setView(ll).setCancelable(false).show();

				bu3.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View p1)
						{
							ed2.setText(ed2.getText().toString()+"&");
							ed2.setSelection(ed2.getText().toString().length());
						}
					});
				
				
				bu1.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View p1)
						{
							String title = ed1.getText().toString();
							String text = ed2.getText().toString();

							if (!title.isEmpty() && !text.isEmpty())
							{
								//
								try
								{
									FileWriter fw = new FileWriter(root + title + ".gh");

									fw.write(text);

									fw.flush();

									fw.close();
									ad.dismiss();
									f5();
								}
								catch (IOException e)
								{
									Toast.makeText(getApplicationContext(), "" + e, 1).show();
								}


							}				
						}
					});
				bu2.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View p1)
						{
							// TODO: Implement this method
							ad.dismiss();
						}
					});

				return true;
			case android.R.id.home: 
				// 处理返回逻辑
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}


	//菜单 
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.filelist, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
