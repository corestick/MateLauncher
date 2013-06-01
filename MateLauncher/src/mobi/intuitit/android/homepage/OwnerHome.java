package mobi.intuitit.android.homepage;
import java.io.File;
import java.util.ArrayList;

import mobi.intuitit.android.mate.launcher.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


//import org.omg.CORBA.portable.CustomValue;

public class OwnerHome extends Activity  implements OnScrollListener {

	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;
	
	private String sdcard = Environment.getExternalStorageDirectory()
			.getAbsolutePath();
	
//	private static final int TEXT_DIALOG = 0;

	private Uri mImageCaptureUri;
	private ImageView btnProfile;	
	private TextView profileMsg;	
	private EditText editText; 
	private String data;
	private Gallery gallery;	//��ó �̹��� �����ֱ�
	private Button btnDown;
	
	private ScrollAdapter mAdapter;
	private ListView mListView;
	private LayoutInflater mInflater;
	private ArrayList<String> mRowList;
	private boolean mLockListView;
	public int msgCnt = 20;
	
	private TextView tv_Recommend;
	private TextView tv_Download;
	private TextView tv_Visit;
	
	public int count_Recommend = 0;
	public int count_Download = 0;
	public int count_Visit = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_owner_home);
		

        mRowList = new ArrayList<String>();
        mLockListView = true;
        
        mAdapter = new ScrollAdapter(this, R.layout.row, mRowList);
        mListView = (ListView) findViewById(R.id.owner_listView);
        
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView.addFooterView(mInflater.inflate(R.layout.footer, null));
        
        mListView.setOnScrollListener((OnScrollListener) this);
        mListView.setAdapter(mAdapter);
        
        btnProfile = (ImageView) findViewById(R.id.owner_profile);
//        btnProfile.setImageURI(Uri.fromFile(new File(sdcard+"screen1.jpg")));
		profileMsg = (TextView) findViewById(R.id.owner_state);
		btnDown = (Button)findViewById(R.id.btnDown);
		
		// ��õ, �ٿ�, �湮 �ؽ�Ʈ��		
		tv_Recommend = (TextView)findViewById(R.id.owner_recommend);
		tv_Download = (TextView)findViewById(R.id.owner_down);
		tv_Visit = (TextView)findViewById(R.id.owner_visit);
		
		// ��õ, �ٿ�, �湮 ��
		tv_Recommend = setRecommend(count_Recommend);
		tv_Download = setDownload(count_Download);
		tv_Visit = setVisit(count_Visit);
		
	    // ��ó Ȩ��ũ�� �����ִ� �κ�		
		Gallery gallery = (Gallery)findViewById(R.id.gallery1);
	    gallery.setAdapter(new OwnerImageAdapter(this));
	    
	    gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				CustomDialog dialog = new CustomDialog(OwnerHome.this,position);
				dialog.show();				
			}
		});    
 	
	    addItems(10);	

	    
	    // Guest Ȩȭ������ ���� ���� �ӽ�
	    btnDown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OwnerHome.this, GuestHome.class);
				startActivity(intent);				
			}
		});	    
	    
	    profileMsg.setOnClickListener(new View.OnClickListener() {
	    	@SuppressWarnings("deprecation")
			public void onClick(View v){
	    		Context mContext = OwnerHome.this;
	    		AlertDialog.Builder builder;
	    		AlertDialog dialog;
	    		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
	    		View layout = inflater.inflate(R.layout.textdialog, (ViewGroup)findViewById(R.id.setTextDialog));
	    		
	    		editText = (EditText) layout.findViewById(R.id.editProfile);
	    		
	    		builder = new AlertDialog.Builder(mContext);
	    		builder.setView(layout);
	    		dialog = builder.create();
	    		dialog.setTitle("������ ����");
	    		
	    		dialog.setButton("Ȯ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						// TODO Auto-generated method stub
						data = editText.getText().toString();
						profileMsg.setText(data);
						dialog.dismiss();						
					}
				});	 
	    		dialog.setButton2("���", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						dialog.dismiss();						
					}
				});	 
	    		dialog.show();	    		
	    	}
	    });
	    


		btnProfile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						doTakePhotoAction();
					}
				};

				DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						doTakeAlbumAction();
					}
				};

				DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				};

				new AlertDialog.Builder(OwnerHome.this)
						.setTitle("���ε��� �̹��� ����")
						.setPositiveButton("�����Կ�", cameraListener)
						.setNeutralButton("�ٹ�����", albumListener)
						.setNegativeButton("���", cancelListener).show();

			}
		});

	}
	
	// ��õ, �ٿ�, �湮 �� ��
	private TextView setRecommend(int n){
		tv_Recommend.setText("��õ : " + n);	
		return tv_Recommend;	
	}
	private TextView setDownload(int n){
		tv_Download.setText("�ٿ� : " + n);	
		return tv_Download;	
	}
	private TextView setVisit(int n){
		tv_Visit.setText("�湮 : " + n);	
		return tv_Visit;	
	}

	private void doTakePhotoAction() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		String url = "tmp_" + String.valueOf(System.currentTimeMillis())
				+ ".jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), url));

		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);

		startActivityForResult(intent, PICK_FROM_CAMERA);
	}

	private void doTakeAlbumAction() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case CROP_FROM_CAMERA: {

			Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
			if (thumbnail != null) {
				ImageView profile = (ImageView) findViewById(R.id.owner_profile);
				profile.setImageBitmap(thumbnail);
			}
			File f = new File(mImageCaptureUri.getPath());
			if (f.exists()) {
				f.delete();
			}

			break;
		}

		case PICK_FROM_ALBUM: {
			mImageCaptureUri = data.getData();
		}

		case PICK_FROM_CAMERA: {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(mImageCaptureUri, "image/*");

			intent.putExtra("outputX", 90);
			intent.putExtra("outputY", 90);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			startActivityForResult(intent, CROP_FROM_CAMERA);

			break;
		}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
	    int visibleItemCount, int totalItemCount)
	    {
	      // ���� ���� ó���� ���̴� ����ȣ�� �������� ����ȣ�� ���Ѱ���
	      // ��ü�� ���ڿ� ���������� ���� �Ʒ��� ��ũ�� �Ǿ��ٰ� �����մϴ�.
	      int count = totalItemCount - visibleItemCount;
	      
	      if(msgCnt !=0){
		      if(firstVisibleItem >= count && totalItemCount != 0
		        && mLockListView == false)
		      {
		        addItems(5);
		      }  
	      }
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	private void addItems(final int size)
	  {
	    // �������� �߰��ϴ� ���� �ߺ� ��û�� �����ϱ� ���� ���� �ɾ�Ӵϴ�.
	    mLockListView = true;
	    
	    Runnable run = new Runnable()
	    {
	      @Override
	      public void run()
	      {
	        for(int i = 0 ; i < size ; i++)
	        {
	          mRowList.add("�ȳ� " + msgCnt );
	          msgCnt--;
	        }
	        
	        // ��� �����͸� �ε��Ͽ� �����Ͽ��ٸ� ����Ϳ� �˸���
	        // ����Ʈ���� ���� �����մϴ�.
	        mAdapter.notifyDataSetChanged();
	        if(msgCnt !=0)
	        mLockListView = false;
	        else
	        mLockListView = true;
	      }
	    };
	    
	    // �ӵ��� �����̸� �����ϱ� ���� �ļ�
	    Handler handler = new Handler();
	    handler.postDelayed(run, 5000);
	  }
	
	public class CustomDialog extends Dialog implements android.view.View.OnClickListener{
		ImageView iv;
		public CustomDialog(Context context,int position){
			super(context);			
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			setContentView(R.layout.dialog_screen);
		
		iv = (ImageView) findViewById(R.id.dialog_imageview);
		Uri uri = Uri.fromFile(new File(sdcard+
				"/Test/screen"+position+".jpg"));
		iv.setImageURI(uri);
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		iv.setOnClickListener(this);
		}
		public void onClick(View view){
			if(view == iv){
				dismiss();
			}
		}
	}
	
	
}