package com.amiral.kupluk;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

public class PetaMasjid extends MapActivity{
	
	private MapView mapView;
	private MapController mapController;
	private GeoPoint p;
	
	public class PetaMasjidOverlay extends com.google.android.maps.Overlay{
		
		@Override
        public boolean onTouchEvent(MotionEvent event, MapView mapView) 
        {   
            //---when user lifts his finger---
            if (event.getAction() == 1) {                
                GeoPoint p = mapView.getProjection().fromPixels(
                    (int) event.getX(),
                    (int) event.getY());
                    Toast.makeText(getBaseContext(), 
                        p.getLatitudeE6() / 1E6 + "," + 
                        p.getLongitudeE6() /1E6 , 
                        Toast.LENGTH_SHORT).show();
            }                            
            return false;
        }      
		
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
		{
			super.draw(canvas, mapView, shadow);                   
			 
	        //---translate the GeoPoint to screen pixels---
	        Point screenPts = new Point();
	        mapView.getProjection().toPixels(p, screenPts);

	        //---add the marker---
	        Bitmap bmp = BitmapFactory.decodeResource(
	            getResources(), R.drawable.home);            
	        canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);         
	        return true;

		}
		
		  

	}
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.peta_masjid);
		
		//get a reference to the MapView
		mapView = (MapView) findViewById(R.id.map_masjid);
		
		//Get the Map View's controller
		mapController = mapView.getController();
		
		//Configure the map display options
		mapView.setStreetView(true);
//		mapView.displayZoomControls(true);
		
		//zoom Control
		// Adding zoom controls to Map
/*
		ZoomControls zoomControls = (ZoomControls) mapView.getZoomControls();

//		zoomControls.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
//		LayoutParams.WRAP_CONTENT));
		zoomControls.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		mapView.addView(zoomControls);

		mapView.displayZoomControls(true);
*/
		
		LinearLayout zoomLayout=(LinearLayout) findViewById(R.id.zoom);
		View zoomView=mapView.getZoomControls();
		
		zoomLayout.addView(zoomView,
				new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		
		mapView.displayZoomControls(true);
		
		mapController= mapView.getController();
		String coordinate[]={"-6.170252","106.653471"};
		double lat=Double.parseDouble(coordinate[0]);
		double lon=Double.parseDouble(coordinate[1]);
		
		p=new GeoPoint((int) (lat*1E6), (int)(lon*1E6));
		
		mapController.animateTo(p);
		mapController.setZoom(15);
		
		//---Add a location marker---
        PetaMasjidOverlay mapOverlay = new PetaMasjidOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);        

		mapView.invalidate();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
