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
 * 百度地图学习
 * @author zk 
 * @createDate 20170419
 *
 */
public class MainActivity extends Activity {
	
//	private BMapManager manager;
	private MapView mMapView = null;
	private BaiduMap mBaiduMap = null;
	
	//自己定义的BDLocation 本来用来接收BDLocationListener结果监听接口的BDLocation值但由于空指针的问题而弃用
//	private BDLocation myBDLocation;
	
	private LocationClient mLocationClient = null;
	private BDLocationListener  myListener = new MyLocationListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.activity_main);
		
		//声明LocationClient类
		mLocationClient = new LocationClient(getApplicationContext());     
		 //注册监听函数
	    mLocationClient.registerLocationListener( myListener );    
	    initLocation();
	    //开始定位
	    mLocationClient.start();
	    
		 //获取地图控件引用  
        mMapView = (MapView) findViewById(R.id.bmapView);  
        
        
        mBaiduMap = mMapView.getMap();  
       /*//标注图标
        //定义Maker坐标点  
        LatLng point = new LatLng(31.963175, 118.400244);
//	    LatLng point = new LatLng(myBDLocation.getAltitude(),myBDLocation.getLongitude());  
	    //构建Marker图标  
	    BitmapDescriptor bitmap = BitmapDescriptorFactory  
	        .fromResource(R.drawable.ic_launcher);  
	    //构建MarkerOption，用于在地图上添加Marker  
	    OverlayOptions option = new MarkerOptions()  
	        .position(point)  
	        .icon(bitmap);  
	    //在地图上添加Marker，并显示  
	    mBaiduMap.addOverlay(option);*/
        
        //定位
     // 开启定位图层  
        mBaiduMap.setMyLocationEnabled(true);  
        
     // 构造定位数据  见BDLocationListener结果监听接口，异步获取定位结果中
    /* // 构造定位数据  
        MyLocationData locData = new MyLocationData.Builder()  
            .accuracy(location.getRadius())  
            // 此处设置开发者获取到的方向信息，顺时针0-360  
            .direction(100).latitude(myBDLocation.getLatitude())  
            .longitude(location.getLongitude()).build();  
        // 设置定位数据  
        mBaiduMap.setMyLocationData(locData);*/
        
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）  
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory  
            .fromResource(R.drawable.icon_geo);  
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfiguration(config);  
        // 当不需要定位图层时关闭定位图层  
//        mBaiduMap.setMyLocationEnabled(false);
        
        //设置缩放级别
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(16).build()));
        
        /** 
         *MapStatusUpdateFactory.newLatLng(LatLng latLng):设置地图新中心点  见BDLocationListener结果监听接口，异步获取定位结果中
         * */  
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(new LatLng(myBDLocation.getLatitude(),myBDLocation.getLongitude()));  
//        mBaiduMap.animateMapStatus(u);  
       
	}

	 @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
        mLocationClient.stop();
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        }  
    /**
     * LocationClientOption类，该类用来设置定位SDK的定位方式
     */
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
     
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
     
        int span=1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
     
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
     
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
     
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
     
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
     
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
     
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死  
     
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
     
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
     
        mLocationClient.setLocOption(option);
    }
    /**
     * BDLocationListener为结果监听接口，异步获取定位结果
     * @author baidu
     *
     */
    public class MyLocationListener implements BDLocationListener {
    	 
        @Override
        public void onReceiveLocation(BDLocation location) {
     
            //获取定位结果
            StringBuffer sb = new StringBuffer(256);
     
            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间
     
            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型
     
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息
     
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息
     
            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度
     
            if (location.getLocType() == BDLocation.TypeGpsLocation){
     
                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时
     
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数
     
                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米
     
                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度
     
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
     
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
     
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
     
                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
     
                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息
     
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
     
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
     
                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
     
            } else if (location.getLocType() == BDLocation.TypeServerError) {
     
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
     
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
     
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
     
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
     
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
     
            }
     
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息
     
            List<Poi> list = location.getPoiList();    // POI数据
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
         // 构造定位数据  
            MyLocationData locData = new MyLocationData.Builder()  
                .accuracy(location.getRadius())  
                // 此处设置开发者获取到的方向信息，顺时针0-360  
                .direction(100).latitude(location.getLatitude())  
                .longitude(location.getLongitude()).build();  
            // 设置定位数据  
            mBaiduMap.setMyLocationData(locData);  
            
            /** 
             *MapStatusUpdateFactory.newLatLng(LatLng latLng):设置地图新中心点 
             * */  
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));  
            mBaiduMap.animateMapStatus(u); 
            
          //定义Maker坐标点  
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());  
            //构建Marker图标  
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
            //构建MarkerOption，用于在地图上添加Marker  
            OverlayOptions option = new MarkerOptions()  
                .position(point)  
                .icons(bitMapList);
            //在地图上添加Marker，并显示  
            mBaiduMap.addOverlay(option);
            
        }

		@Override
		public void onConnectHotSpotMessage(String arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

    }
}
