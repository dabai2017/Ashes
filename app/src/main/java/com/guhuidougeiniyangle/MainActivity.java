package com.guhuidougeiniyangle;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
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
import android.widget.DatePicker.*; 



public class MainActivity extends Activity implements OnInitListener 
{
	//定义一个tts对象
	private TextToSpeech tts;

	EditText ed1,ed2,ed3,ed4;

	Switch sw1;

	TextView check;

	boolean bo;

	Context mContext;

	String check2 = null;


	
	
	private static final float BEEP_VOLUME = 9.10f;
	private MediaPlayer mediaPlayer;

	private PopupWindow pw;
	private void initBeepSound()
	{
	
		if (mediaPlayer == null)
		{
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.

			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			//mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.lgly);
			try
			{
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			}
			catch (IOException e)
			{
				mediaPlayer = null;
			}
		}
	}



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


		init();


		ed1 = (EditText)findViewById(R.id.mainEditText1);
		ed2 = (EditText)findViewById(R.id.mainEditText2);
		ed3 = (EditText)findViewById(R.id.mainEditText3);
		ed4 = (EditText)findViewById(R.id.mainEditText4);
		sw1 = (Switch)findViewById(R.id.mainSwitch1);

		
		
		
		check = (TextView)findViewById(R.id.maincheck);

		mContext = getApplicationContext();

		sw1.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					// TODO: Implement this method   

					bo = p2;


				}
			});


		//初始化TTS
        tts = new TextToSpeech(this, this);
    }

	private void init()
	{
		// TODO: Implement this method

		File fil = new File("sdcard/骨灰扬了夫/文本文件列表");

		if (!fil.exists() && !fil.isDirectory())
		{
			fil.mkdirs();
		}


		File fil2 = new File("sdcard/骨灰扬了夫/真人语音列表");

		if (!fil2.exists() && !fil2.isDirectory())
		{
			fil2.mkdirs();
		}



	}


	public void yang(View v)
	{

		disUI();


		if (!ed1.getText().toString().isEmpty() && !ed2.getText().toString().isEmpty())
		{


			if (bo)
			{
				if (!ed4.getText().toString().isEmpty())
				{
					new Thread() {
						@Override
						public void run()
						{
							super.run();

							try
							{
								Thread.sleep(Integer.parseInt(ed4.getText().toString())*1000);
							}
							catch (InterruptedException e)
							{}

							String te = "肏你妈，三天之内" + ed1.getText().toString() + ",把你" + ed2.getText().toString() + "都给你扬喽";
							tts.speak(te, TextToSpeech.QUEUE_FLUSH, null);

						}
					}.start();
				}else{
					alert("延时不能为空");
				}
			}

			else
			{
				String te = "肏你妈，三天之内" + ed1.getText().toString() + ",把你" + ed2.getText().toString() + "都给你扬喽";
				tts.speak(te, TextToSpeech.QUEUE_FLUSH, null);
			}

		}
		else
		{
			alert("空白文本不能读");
		}




	}

	private void alert(String p0)
	{
		new AlertDialog.Builder(this).setMessage(p0).show();
	}


	public void yuedu(View v)
	{

		disUI();

		if (bo)
		{

			if (!ed4.getText().toString().isEmpty())
			{
			new Thread() {
				@Override
				public void run()
				{
					super.run();

					try
					{
						Thread.sleep(Integer.parseInt(ed4.getText().toString())*1000);
					}
					catch (InterruptedException e)
					{}

					String te = ed3.getText().toString();

					tts.speak(te, TextToSpeech.QUEUE_FLUSH, null);


				}
			}.start();

			}else{
				alert("延时不能为空");
			}


		}

		else
		{
			String te = ed3.getText().toString();

			tts.speak(te, TextToSpeech.QUEUE_FLUSH, null);

		}



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
				check.setBackgroundColor(Color.RED);
				check2 = "不支持";
				//Toast.makeText(MainActivity.this, "本设备不支持中文发声", Toast.LENGTH_SHORT).show(); 
			}
			else
			{
				check.setBackgroundColor(Color.GREEN);
				check2 = "支持";
			} 
		} 
	} 



	public void toqq(View v)
	{
		Intent resolveIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
		getApplicationContext().startActivity(resolveIntent);
	}


	public void towx(View v)
	{
		Intent resolveIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
		getApplicationContext().startActivity(resolveIntent);
	}

    public void disUI()
	{
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);


    }



	//菜单 
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{


			case R.id.gdzrhh:
				//快捷喊话 寒王  药水

				startActivity(new Intent(this, VoiceList.class));
				
				break;



			case R.id.gdwbhh:
				//快捷喊话  读取文件

				startActivity(new Intent(this, FileList.class));

				break;

			case R.id.sybz:
				//帮助

				new AlertDialog.Builder(this).setTitle("帮助").
					setMessage("1、指示灯会告诉你，你的设备是否支持中文阅读\n2、上边的你输入要做的事和需要扬的东西，会自动帮你扬了它\n3、下边的可以自己打字，然后让程序阅读哦\n4、可以在右上角的按钮里发现更多好玩的功能").show();

				break;

				case R.id.kydz:
				Uri uri2=Uri.parse("https://github.com/dabai2017/Ashes.git");
				Intent inten2t=new Intent(Intent.ACTION_VIEW,uri2);
				startActivity(inten2t);
					
					break;
				
			case R.id.fxrj:
				
				
				//分享
				
				String link = "https://www.lanzous.com/b660041";
				Intent intent = new Intent(Intent.ACTION_SEND); //启动分享发送的属性    
				intent.setType("text/plain");//分享发送的数据类型为文本  
				intent.putExtra(Intent.EXTRA_SUBJECT, "分享下载地址");    //分享的主题    
				intent.putExtra(Intent.EXTRA_TEXT, "程序名 ： 骨灰扬了夫\n下载地址 ： " + link);    //分享的内容    
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				this.startActivity(Intent.createChooser(intent, "分享下载地址"));//目标应用选择对话框的标题


				
						
				break;

			case R.id.gyrj:
				//关于

				new AlertDialog.Builder(this).setTitle("关于").
					setMessage("程序 ： 骨灰扬了夫\n包名 ： " + getPackageName() + "\n中文 ： " + check2 + "\n开发 ： fenghaichen").show();



				break;



			case R.id.lgly:
				//视频

				VideoView vv = new VideoView(this);
				String uri = "android.resource://" + getPackageName() + "/" + R.raw.lgly;
				vv.setVideoURI(Uri.parse(uri));
				vv.start();	

				pw = new PopupWindow(vv, 500, 500);


				pw.showAsDropDown(check);



				vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() 
					{ 
						@Override 
						public void onCompletion(MediaPlayer mp) 
						{ 
							pw.dismiss(); 
						} 
					}); 



				break;
			case R.id.ybyy:

				initBeepSound();
				mediaPlayer.start();
				break;


			case R.id.yqsz:
//跳转到文字转语音设置界面
				Intent inset = new Intent();
				inset.setAction("com.android.settings.TTS_SETTINGS");
				inset.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				this.startActivity(inset);
				break;

			case R.id.xunfei:


				if (isMobile_spExist())
				{
					Intent resolveIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.iflytek.vflynote");
					getApplicationContext().startActivity(resolveIntent);

				}

				else
				{	

					try
					{
						c("xunfei.apk", "sdcard/xunfei.apk");
					}
					catch (IOException e)
					{}



					Intent in = new Intent(Intent.ACTION_VIEW);
					in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					in.setDataAndType(Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/xunfei.apk"),
									  "application/vnd.android.package-archive");

					mContext.startActivity(in);
					break;

				}

        }
        return super.onOptionsItemSelected(item);
    }



	
	
	
	public boolean isMobile_spExist()
	{
        PackageManager manager = this.getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++)
		{
            PackageInfo pI = pkgList.get(i);
            if (pI.packageName.equalsIgnoreCase("com.iflytek.vflynote"))
                return true;
        }
        return false;
    }


	public void c(String assetsFileName, String OutFileName) throws IOException 
	{
        File f = new File(OutFileName);
        if (f.exists())
            f.delete();
        f = new File(OutFileName);
        f.createNewFile();
        InputStream I = getAssets().open(assetsFileName);
        OutputStream O = new FileOutputStream(OutFileName);
        byte[] b = new byte[1024];
        int l = I.read(b);
        while (l > 0) 
		{
            O.write(b, 0, l);
            l = I.read(b);
        }
        O.flush();
        I.close();
        O.close();
    }




}
