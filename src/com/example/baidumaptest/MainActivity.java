package com.example.baidumaptest;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
/**
 * �ٶȵ�ͼѧϰ
 * @author zk 
 * @createDate 20170419
 *
 */
public class MainActivity extends Activity {
	
//	private BMapManager manager;
	private MapView mMapView = null;
	private BaiduMap mBaiduMap = null;
	
	//�Լ������BDLocation ������������BDLocationListener��������ӿڵ�BDLocationֵ�����ڿ�ָ������������
//	private BDLocation myBDLocation;
	
	private LocationClient mLocationClient = null;
	private BDLocationListener  myListener = new MyLocationListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext  
        //ע��÷���Ҫ��setContentView����֮ǰʵ��  
        SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.activity_main);
		
		//����LocationClient��
		mLocationClient = new LocationClient(getApplicationContext());     
		 //ע���������
	    mLocationClient.registerLocationListener( myListener );    
	    initLocation();
	    //��ʼ��λ
	    mLocationClient.start();
	    
		 //��ȡ��ͼ�ؼ�����  
        mMapView = (MapView) findViewById(R.id.bmapView);  
        
        
        mBaiduMap = mMapView.getMap();  
       /*//��עͼ��
        //����Maker�����  
        LatLng point = new LatLng(31.963175, 118.400244);
//	    LatLng point = new LatLng(myBDLocation.getAltitude(),myBDLocation.getLongitude());  
	    //����Markerͼ��  
	    BitmapDescriptor bitmap = BitmapDescriptorFactory  
	        .fromResource(R.drawable.ic_launcher);  
	    //����MarkerOption�������ڵ�ͼ�����Marker  
	    OverlayOptions option = new MarkerOptions()  
	        .position(point)  
	        .icon(bitmap);  
	    //�ڵ�ͼ�����Marker������ʾ  
	    mBaiduMap.addOverlay(option);*/
        
        //��λ
     // ������λͼ��  
        mBaiduMap.setMyLocationEnabled(true);  
        
     // ���춨λ����  ��BDLocationListener��������ӿڣ��첽��ȡ��λ�����
    /* // ���춨λ����  
        MyLocationData locData = new MyLocationData.Builder()  
            .accuracy(location.getRadius())  
            // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360  
            .direction(100).latitude(myBDLocation.getLatitude())  
            .longitude(location.getLongitude()).build();  
        // ���ö�λ����  
        mBaiduMap.setMyLocationData(locData);*/
        
        // ���ö�λͼ������ã���λģʽ���Ƿ���������Ϣ���û��Զ��嶨λͼ�꣩  
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory  
            .fromResource(R.drawable.icon_geo);  
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfiguration(config);  
        // ������Ҫ��λͼ��ʱ�رն�λͼ��  
//        mBaiduMap.setMyLocationEnabled(false);
        
        //�������ż���
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(16).build()));
        
        /** 
         *MapStatusUpdateFactory.newLatLng(LatLng latLng):���õ�ͼ�����ĵ�  ��BDLocationListener��������ӿڣ��첽��ȡ��λ�����
         * */  
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(new LatLng(myBDLocation.getLatitude(),myBDLocation.getLongitude()));  
//        mBaiduMap.animateMapStatus(u);  
       
	}

	 @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onDestroy();  
        mLocationClient.stop();
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onPause();  
        }  
    /**
     * LocationClientOption�࣬�����������ö�λSDK�Ķ�λ��ʽ
     */
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        //��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
     
        option.setCoorType("bd09ll");
        //��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
     
        int span=1000;
        option.setScanSpan(span);
        //��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
     
        option.setIsNeedAddress(true);
        //��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
     
        option.setOpenGps(true);
        //��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
     
        option.setLocationNotify(true);
        //��ѡ��Ĭ��false�������Ƿ�GPS��Чʱ����1S/1��Ƶ�����GPS���
     
        option.setIsNeedLocationDescribe(true);
        //��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
     
        option.setIsNeedLocationPoiList(true);
        //��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
     
        option.setIgnoreKillProcess(false);
        //��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��  
     
        option.SetIgnoreCacheException(false);
        //��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
     
        option.setEnableSimulateGps(false);
        //��ѡ��Ĭ��false�������Ƿ���Ҫ����GPS��������Ĭ����Ҫ
     
        mLocationClient.setLocOption(option);
    }
    /**
     * BDLocationListenerΪ��������ӿڣ��첽��ȡ��λ���
     * @author baidu
     *
     */
    public class MyLocationListener implements BDLocationListener {
    	 
        @Override
        public void onReceiveLocation(BDLocation location) {
     
            //��ȡ��λ���
            StringBuffer sb = new StringBuffer(256);
     
            sb.append("time : ");
            sb.append(location.getTime());    //��ȡ��λʱ��
     
            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //��ȡ��������
     
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //��ȡγ����Ϣ
     
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //��ȡ������Ϣ
     
            sb.append("\nradius : ");
            sb.append(location.getRadius());    //��ȡ��λ��׼��
     
            if (location.getLocType() == BDLocation.TypeGpsLocation){
     
                // GPS��λ���
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // ��λ������ÿСʱ
     
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //��ȡ������
     
                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //��ȡ���θ߶���Ϣ����λ��
     
                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //��ȡ������Ϣ����λ��
     
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //��ȡ��ַ��Ϣ
     
                sb.append("\ndescribe : ");
                sb.append("gps��λ�ɹ�");
     
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
     
                // ���綨λ���
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //��ȡ��ַ��Ϣ
     
                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //��ȡ��Ӫ����Ϣ
     
                sb.append("\ndescribe : ");
                sb.append("���綨λ�ɹ�");
     
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
     
                // ���߶�λ���
                sb.append("\ndescribe : ");
                sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
     
            } else if (location.getLocType() == BDLocation.TypeServerError) {
     
                sb.append("\ndescribe : ");
                sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
     
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
     
                sb.append("\ndescribe : ");
                sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
     
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
     
                sb.append("\ndescribe : ");
                sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
     
            }
     
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //λ�����廯��Ϣ
     
            List<Poi> list = location.getPoiList();    // POI����
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
     
            Log.d("MainActivity", sb.toString());
//            Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
//            myBDLocation = location;
         // ���춨λ����  
            MyLocationData locData = new MyLocationData.Builder()  
                .accuracy(location.getRadius())  
                // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360  
                .direction(100).latitude(location.getLatitude())  
                .longitude(location.getLongitude()).build();  
            // ���ö�λ����  
            mBaiduMap.setMyLocationData(locData);  
            
            /** 
             *MapStatusUpdateFactory.newLatLng(LatLng latLng):���õ�ͼ�����ĵ� 
             * */  
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));  
            mBaiduMap.animateMapStatus(u); 
            
          //����Maker�����  
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());  
            //����Markerͼ��  
            ArrayList<BitmapDescriptor> bitMapList = new ArrayList<BitmapDescriptor>();
            //BitmapDescriptor bitmap1 = BitmapDescriptorFactory  
            //    .fromResource(R.drawable.left);
            BitmapDescriptor bitmap2 = BitmapDescriptorFactory  
                    .fromResource(R.drawable.middle); 
            //BitmapDescriptor bitmap3 = BitmapDescriptorFactory  
            //        .fromResource(R.drawable.right); 
           // bitMapList.add(bitmap1);
            bitMapList.add(bitmap2);
            //bitMapList.add(bitmap3);
            //����MarkerOption�������ڵ�ͼ�����Marker  
            OverlayOptions option = new MarkerOptions()  
                .position(point)  
                .icons(bitMapList);
            //�ڵ�ͼ�����Marker������ʾ  
            mBaiduMap.addOverlay(option);
            
        }

		@Override
		public void onConnectHotSpotMessage(String arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

    }
}
