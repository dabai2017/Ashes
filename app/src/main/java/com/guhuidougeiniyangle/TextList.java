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
import org.apache.http.util.*;
import android.widget.AdapterView.*; 

public class TextList extends Activity implements OnInitListener 
{
	
	Context mContext;
	
	String root = "/sdcard/骨灰扬了夫/文本文件列表/";

	ListView lv;

	private String[] data;
	
	//定义一个tts对象
	private TextToSpeech tts;

	
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textlist);
		mContext = getApplicationContext();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		lv = (ListView)findViewById(R.id.textlistListView1);
		
		init();
		
		
		
		lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					
					try{
					// TODO: Implement this method
					String text = data[p3];
					//喊话
					tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
					}catch(Exception e){
						toast("异常:请重新打开软件"+e);
					}
					
				}
			});
		
		
		
		
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();

		tts.stop();
		tts = null;



	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		
		//初始化TTS
        tts = new TextToSpeech(this, this);
		
		
	}
	
	
	
	
	
	
	public void onInit(int status)
	{ 
		// 判断是否转化成功 
		if (status == TextToSpeech.SUCCESS)
		{ 
			//默认设定语言为中文，原生的android貌似不支持中文。
			int result = tts.setLanguage(Locale.CHINESE); 
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
			{ 
				
				Toast.makeText(this, "本设备不支持中文发声", Toast.LENGTH_SHORT).show(); 
			}
			else
			{
			
			} 
		} 
	} 
	
	
	
	
	private void init()
	{
		// TODO: Implement this method
		Intent intent=getIntent();
        String result=intent.getStringExtra("result");

		String filepath = root+result;
		setTitle(result);
		
		try
		{
			String text = readSDFile(filepath);
			data=text.split("&");
			f5();
		}
		catch (IOException e)
		{
			toast("异常"+e);
		}

		
	}

	private void toast(String p0)
	{
		Toast.makeText(getApplicationContext(),p0,1).show();
	}

	
	public String readSDFile(String fileName) throws IOException {    

        File file = new File(fileName);    
        FileInputStream fis = new FileInputStream(file);    
        int length = fis.available();   
		byte [] buffer = new byte[length];   
		fis.read(buffer); 
		String res = EncodingUtils.getString(buffer, "UTF-8");
		fis.close();       
		return res;    
	} 
	
	private void f5()
	{
		// TODO: Implement this method
		ArrayAdapter ad = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
		lv.setAdapter(ad);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
			case android.R.id.home: 
				// 处理返回逻辑
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
}
