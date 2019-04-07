package com.guhuidougeiniyangle;
import android.app.*;
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
import android.animation.*;
import android.view.animation.*;
import java.text.*;
import android.support.design.widget.*;
import android.database.*;
import android.provider.*;
import android.annotation.*;


public class VoiceList extends Activity
{




	Button bu1,bu2,bu3;


	EditText ed1,ed2;

	TextView voicecheck;

	LinearLayout ll1;

	String lastFilename;


	private Context mContext;

	String root = "/sdcard/骨灰扬了夫/真人语音列表/";

	ListView lv;

	MediaPlayer mMediaPlayer= new MediaPlayer();


	private ArrayList<String> data1;

	private File file;
	private static final float BEEP_VOLUME = 9.10f;

	private MediaPlayer mediaPlayer;

	private MediaRecorder mMediaRecorder;

	private File mRecAudioFile;

	private static final int REQUEST_FILE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voicelist);
		mContext = getApplicationContext();
		getActionBar().setDisplayHomeAsUpEnabled(true);


		ll1 = (LinearLayout)findViewById(R.id.voicelistLinearLayout1);

		ed1 = (EditText)findViewById(R.id.voicelistEditText1);

		ed2 = (EditText)findViewById(R.id.voicelistEditText2);

		voicecheck = (TextView)findViewById(R.id.voicecheck);
		voicecheck.setBackgroundColor(Color.RED);



		ed2.setText(Build.MODEL);


		lv = (ListView)findViewById(R.id.voicelistListView1);

		file = new File(root);

		f5();



		bu1 = (Button)findViewById(R.id.voicelistButton1);
		bu2 = (Button)findViewById(R.id.voicelistButton2);
		bu3 = (Button)findViewById(R.id.voicelistButton3);


		lv.setOnItemClickListener(new OnItemClickListener(){

				private String filpath;

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{

					try{
					
					filpath = root + data1.get(p3);


					if (mediaPlayer != null)
					{
						mediaPlayer.stop();
						mediaPlayer = null;
					}
					mediaPlayer = new MediaPlayer();
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

					try
					{
						mediaPlayer.setDataSource(filpath);
						mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
						mediaPlayer.prepare();
					}
					catch (IOException e)
					{
						mediaPlayer = null;


					}

					mediaPlayer.start();
					
					}catch(Exception e){
						toast("异常:不是音频文件");
					}

				}
				
				


			});





		lv.setOnItemLongClickListener(new OnItemLongClickListener(){

				private File file1;

				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					String filename = data1.get(p3); 
					String filepath = root + filename;

					file1 = new File(filepath);


					new AlertDialog.Builder(VoiceList.this)
						.setTitle("提示")
						.setMessage("是否删除" + data1.get(p3) + "?")
						.setPositiveButton("取消", null) 
						.setNeutralButton("确认",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i)
							{
								if (file1.delete())
								{
									Toast.makeText(getApplicationContext(), "删除成功", 1).show();
								}
								f5();
							}
						})
						.show();

					return true;
				}


			});



	}




	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();

		if (mediaPlayer != null)
		{
			mediaPlayer.stop();
			mediaPlayer = null;
		}



	}




	public void start(View v)
	{

		Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
		vibrator.vibrate(300);

		//录制按钮
		if (bu1.getText().toString().equals("开始录制"))
		{
			bu1.setText("停止录制");
			voicecheck.setBackgroundColor(Color.GREEN);
			SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
			if (ed1.getText().toString().isEmpty())
			{
				ed1.setText(Build.MODEL + "_" + sdf.format(new Date()) + ".amr");	
			}

			bu2.setEnabled(false);
			bu3.setEnabled(false);

			//开始录制

			/* 创建录音文件，第一个参数是文件名前缀，第二个参数是后缀，第三个参数是SD路径 */  

			try
			{
				mRecAudioFile = new File(root + ed1.getText().toString());
				lastFilename = root + ed1.getText().toString();
				mRecAudioFile.createNewFile();
			}
			catch (IOException e)
			{}

			/* 实例化MediaRecorder对象 */

			mMediaRecorder = new MediaRecorder();

			/* 设置麦克风 */

			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

			/* 设置输出文件的格式 */

			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);

			/* 设置音频文件的编码 */

			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

			/* 设置输出文件的路径 */

			mMediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());

			/* 准备 */

			try
			{
				mMediaRecorder.prepare();
			}
			catch (IOException e)
			{}
			catch (IllegalStateException e)
			{}

			/* 开始 */

			mMediaRecorder.start();


		}
		else
		{
			bu1.setText("开始录制");

			voicecheck.setBackgroundColor(Color.RED);
			bu2.setEnabled(true);
			bu3.setEnabled(true);
			bu1.setEnabled(false);
			//停止录制

			mMediaRecorder.stop();  
			mMediaRecorder.release();  
			mMediaRecorder = null;  


		}	
	}



	//保存代码
	public void save(View v)
	{


		try
		{
			File last = new File(lastFilename);
			File newf = new File(root + ed1.getText().toString());
			newf.createNewFile();	

			if (last.renameTo(newf))
			{
				toast("保存成功");
				f5();
			}

			bu1.setEnabled(true);
			bu2.setEnabled(false);
			bu3.setEnabled(false);

		}
		catch (IOException e)
		{}

	}

	private void toast(String p0)
	{
		// TODO: Implement this method
		Toast.makeText(getApplicationContext(), p0, 1).show();
	}


	//删除

	public void del(View v)
	{

		File last = new File(lastFilename);

		if (last.delete())
		{
			toast("删除成功");
			f5();
		}

		bu1.setEnabled(true);
		bu2.setEnabled(false);
		bu3.setEnabled(false);



	}






	private void f5()
	{
		String dat[] = file.list();
		Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
		// JDKz自带对数组进行排序。
		Arrays.sort(dat, cmp);
		data1 = new ArrayList();
		for (String a :dat)
		{

			data1.add(a);

		}

		ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data1);
		lv.setAdapter(ad);
	}





	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle presses on the action bar items


		switch (item.getItemId())
		{
			case R.id.lz:

				if (ll1.getVisibility() == View.GONE)
				{

					ll1.setVisibility(View.VISIBLE);
					TranslateAnimation ani1 = new TranslateAnimation(0, 0, -1000, 0);
					ani1.setDuration(500);	
					ll1.setAnimation(ani1);


				}
				else
				{

					TranslateAnimation ani1 = new TranslateAnimation(0, 0, 0, -1000);
					ani1.setDuration(500);	
					ll1.setAnimation(ani1);
					ll1.setVisibility(View.GONE);

				}

				return true;

			case R.id.dr:
				//导入

				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*");
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(Intent.createChooser(intent, "Select a File"), REQUEST_FILE);


				return true;

			case android.R.id.home: 
				// 处理返回逻辑
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		
		
		
		

		try
		{

			Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
			
			
			String lastpath = getPath2uri(this, uri);
			File lastf=new File(lastpath); 
			String newpath = root + lastf.getName();
			copyFile(lastpath, newpath);
			f5();
		}
		catch (Exception e)
		{
			toast("导入异常" + e);
		}


		
		
		

	}


	/*
	 * 复制单个文件
	 * @param oldPath String 原文件路径 如：data/video/xxx.mp4
	 * @param newPath String 复制后路径 如：data/oss/xxx.mp4
	 * @return boolean
	 */
    public void copyFile(String oldPath, String newPath)
	{
        try
		{
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            File newFile = new File(newPath);
            if (!newFile.exists())
			{
                newFile.createNewFile();
            }
            if (oldfile.exists())
			{ //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1)
				{
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }        
                inStream.close();
            }
        }
        catch (Exception e)
		{
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();


        }


    }




	//菜单 
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.voicelist, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
     * 根据Uri获取文件的绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param fileUri
     */
    @TargetApi(19)
    public static String getPath2uri(Activity context, Uri fileUri)
	{
        if (context == null || fileUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, fileUri))
		{
            if (isExternalStorageDocument(fileUri))
			{
                String docId = DocumentsContract.getDocumentId(fileUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type))
				{
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
			else if (isDownloadsDocument(fileUri))
			{
                String id = DocumentsContract.getDocumentId(fileUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
			else if (isMediaDocument(fileUri))
			{
                String docId = DocumentsContract.getDocumentId(fileUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type))
				{
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
				else if ("video".equals(type))
				{
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }
				else if ("audio".equals(type))
				{
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(fileUri.getScheme()))
		{
            // Return the remote address
            if (isGooglePhotosUri(fileUri))
                return fileUri.getLastPathSegment();
            return getDataColumn(context, fileUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(fileUri.getScheme()))
		{
            return fileUri.getPath();
        }
        return null;
    }


	/**
     * Android4.4 （<19）以下版本获取uri地址方法
     * @param context           上下文
     * @param uri               返回的uri
     * @param selection         条件
     * @param selectionArgs     值
     * @return                  uri文件所在的路径
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs)
	{
        Cursor cursor = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        try
		{
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst())
			{
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(index);
            }
        }
		finally
		{
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
	/**
     * @param uri  The Uri to check.
     * @return
     *      URI权限是否为ExternalStorageProvider
     *      Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri)
	{
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

	/**
     * @param uri  The Uri to check.
     * @return
     *      URI权限是否为google图片
     *      Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri)
	{
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }



	/**
     * @param uri   The Uri to check.
     * @return
     *      URI权限是否为DownloadsProvider.
     *      Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri)
	{
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


	/**
     * @param uri  The Uri to check.
     * @return
     *      URI权限是否为MediaProvider.
     *      Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri)
	{
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
