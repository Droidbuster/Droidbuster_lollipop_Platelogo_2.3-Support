package com.Droidbuster.droidbuster_LollipopEasterEgg;

import java.util.Random;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Camera;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;


public class MainActivity extends Activity {

	int tapatap;
	SharedPreferences.Editor edit;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final ImageView lollipop = new ImageView(this);
		final ImageView logo 	 = new ImageView(this);
		final RelativeLayout pop = new RelativeLayout(this);
		final ImageView stick    = new ImageView(this);
		final ImageView lollipopLight 	 = new ImageView(this);
		final String[] materialPalette 	 = {"9C27B0", "BA68C8", "FF9800", "FFB74D", "F06292", "F8BBD0", "AFB42B", "CDDC39", "FFEB3B", "FFF176", "795548", "A1887F"};	//hex codes of the Material Design colors. You can find the palette at google.com/design
		final ImageView lightClickEffect = new ImageView(this);

		edit = getSharedPreferences("preferenceggs", Context.MODE_PRIVATE).edit();
		
		RelativeLayout container = new RelativeLayout(this);

		lollipop.setImageResource(R.drawable.lollipop_circle);
		logo.setImageResource(R.drawable.lollipop_text);
		stick.setBackgroundResource(R.drawable.lollipop_stick);
		lollipopLight.setImageResource(R.drawable.lollipop_light);

		final TransitionDrawable lightning = new TransitionDrawable(new Drawable[]{
				getResources().getDrawable(R.drawable.lollipop_white),
				new ColorDrawable(0)
		});
		lightning.setCrossFadeEnabled(true);
		lightClickEffect.setImageDrawable(lightning);


		LayoutParams LollipopLP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LollipopLP.addRule(RelativeLayout.CENTER_IN_PARENT);
		lollipop.setLayoutParams(LollipopLP);

		LayoutParams LollipopLightLP = new LayoutParams(60,60);
		LollipopLightLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		LollipopLightLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		LollipopLightLP.setMargins(19, 17, 0, 0);
		lollipopLight.setLayoutParams(LollipopLightLP);

		LayoutParams LogoLP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LogoLP.addRule(RelativeLayout.CENTER_IN_PARENT);
		logo.setLayoutParams(LogoLP);

		int length = 5;
		if(getSharedPreferences("preferenceggs", Context.MODE_PRIVATE).getString("lp_sysui", getString(R.string.pref_none)).equals("Immersive Mode"))
			length = 50;
		@SuppressWarnings("deprecation")
		LayoutParams StickLP = new LayoutParams(LayoutParams.WRAP_CONTENT, (getWindowManager().getDefaultDisplay().getHeight() / 2) - lollipop.getBottom() + length);
		StickLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		StickLP.addRule(RelativeLayout.CENTER_HORIZONTAL);
		stick.setLayoutParams(StickLP);

		ViewHelper.setAlpha(stick, 0);
		ViewHelper.setAlpha(logo, 0);
		ViewHelper.setAlpha(lightClickEffect, 0.55F);
		logo.setVisibility(0);
		pop.addView(lollipop);
		pop.addView(lollipopLight);
		pop.addView(logo);
		pop.addView(lightClickEffect, LollipopLP);
		LayoutParams popLP = new LayoutParams(140,140);
		popLP.addRule(RelativeLayout.CENTER_IN_PARENT);
		pop.setLayoutParams(popLP);

		container.addView(stick);
		container.addView(pop);

		ViewHelper.setScaleX(pop, 0);
		ViewHelper.setScaleY(pop, 0);

		setContentView(container);

		final Context ctx = this;
		//Decide if the color should be purely random or between the palette
		if (getSharedPreferences("preferenceggs", Context.MODE_PRIVATE).getBoolean("lp_purely_random", false))
			Eggs.setColorFilter(lollipop, new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));	//lollipop color is purely random
		else			
			colorizeHex(lollipop, materialPalette[new Random().nextInt(materialPalette.length)]);	//lollipop color is randomly picked from the material palette

		ViewPropertyAnimator.animate(pop).scaleX(1).scaleY(1).setDuration(500).setInterpolator(new DecelerateInterpolator()).setStartDelay(800).start();
		pop.setClickable(true);
		pop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				if (tapatap==0) {
					ViewPropertyAnimator.animate(pop).scaleX(4.2F).scaleY(4.2F).setDuration(700).setInterpolator(new DecelerateInterpolator()).start();
					ViewPropertyAnimator.animate(logo).alpha(1).setDuration(700).setStartDelay(500).start();
					ViewPropertyAnimator.animate(stick).translationYBy(pop.getMeasuredHeight()*2.1F).alpha(1).setDuration(1000).setStartDelay(1000).start();
				} else {
					lightning.startTransition(200);				//illuminati weren't here
					if (getSharedPreferences("preferenceggs", Context.MODE_PRIVATE).getBoolean("lp_purely_random", false))
						Eggs.setColorFilter(lollipop, new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));	//lollipop color is purely random
					else
						colorizeHex(lollipop, materialPalette[new Random().nextInt(materialPalette.length)]);
				}
				tapatap++;
			}
		});
	
		
	}
	
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		Boolean forcePort = getSharedPreferences("preferenceggs", Context.MODE_PRIVATE).getBoolean("lp_force_port", false);

		if (forcePort == true) {
				
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			
		}
				
	}

	public static void colorizeHex(ImageView iv, String hex) {
		Eggs.setColorFilter(iv,
				Integer.valueOf(hex.substring(0, 2), 16),
				Integer.valueOf(hex.substring(2, 4), 16),
				Integer.valueOf(hex.substring(4, 6), 16));
	}

}

