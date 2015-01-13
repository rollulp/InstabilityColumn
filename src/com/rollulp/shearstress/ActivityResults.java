package com.rollulp.shearstress;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityResults extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		DataParcelable data = getIntent().getParcelableExtra("data");
		
		/*DIALOGO POPUP*/
		if ( data.allFields != 0 ) Toast.makeText( getApplicationContext(), getString(R.string.alert_toast), Toast.LENGTH_LONG).show();
		
		/*I CALCOLI EFFETTIVI*/		
		double lambda = (data.restrains*data.lenght) / Math.sqrt( ((data.base*data.height*data.height*data.height)/12) / (data.base*data.height) );
		double lambda_lim;
		if ( Math.abs(data.minimumMomentum) < Math.abs(data.maximumMomentum) )
			lambda_lim = 15.4 * (1.7 - (data.minimumMomentum/data.maximumMomentum)) /
							Math.sqrt( data.normalForce / (0.85*data.RCK*data.base*data.height));
		else
			lambda_lim = 15.4 * (1.7 - (data.maximumMomentum/data.minimumMomentum)) /
							Math.sqrt( data.normalForce / (0.85*data.RCK*data.base*data.height));
		double E = 22000 * Math.pow(( ( 0.85*0.83*data.RCK/1.5 + 8) / 10 ), 0.3);
		double C = data.base*data.height * 0.85 *0.83* data.RCK / 1.5;
		double CC = Math.PI*Math.PI * E * data.base * data.height / (lambda*lambda);
		double E0 = (data.maximumMomentum==data.minimumMomentum) ?
					(data.maximumMomentum/data.normalForce)	: 
					Math.max(	0.6*data.maximumMomentum/data.normalForce + 0.4*data.minimumMomentum/data.normalForce ,
								0.4*data.maximumMomentum/data.normalForce);
		double Ea = data.lenght*data.restrains/300;
		
		boolean calculate = C > (0.31/2.6*E/1.2*data.base*data.height*data.height*data.height/12/(data.lenght*data.lenght));
		double yc = 0, Ic = 0, Il = 0, Ec = 0, E2 = 0, Etot = 0, MSD = 0;
		if ( calculate ) {
			yc = (data.height*data.height*data.base/2+210000/E*data.superiorArmor*(data.height-30)+210000/E*data.inferiorArmor*30)/
						(data.height*data.base+210000/E*data.superiorArmor+210000/E*data.inferiorArmor);		
			Ic = data.base*data.height*(data.height*data.height/12+(yc-data.height/2)*(yc-data.height/2));
			Il = Ic + 210000/E*data.superiorArmor*yc*yc+210000/E*data.inferiorArmor*(data.height-yc)*(data.height-yc);
			Ec = (E0+Ea) * data.normalForce/CC * Ic/Il / (data.normalForce/CC - (1- Ic/Il) ) * 
					(Math.exp((data.normalForce/CC - (1-Ic/Il) / (1-data.normalForce/CC) * data.viscosity)) - 1);
			E2 = (data.lenght * data.restrains)*(data.lenght*data.restrains) /5 * 0.00186333 / (0.9*(data.height-30));
			Etot = (Double.isNaN(E0)?0:E0)
				 + (Double.isNaN(Ea)?0:Ea) 
				 + (Double.isNaN(Ec)?0:Ec)
				 + (Double.isNaN(E2)?0:E2);
			MSD = data.normalForce * Etot/1000000;
		}
		/*PRO*///double adimensione_normale = normale / (base*altezza*0.83*0.85*RCK/1.5 );
		/*PRO*///double adimensionale_momento = MSD/(base*altezza*altezza*0.85*0.83*RCK/1.5);
		
		/*CAMBIO IL LAYOUT A QUELLO DEI RISULTATI  E LINKO*/
		setContentView(R.layout.activity_results);
		TextView risultato1 = (TextView)findViewById(R.id.risultato1);
		TextView risultato2 = (TextView)findViewById(R.id.risultato2);
		TextView risultato3 = (TextView)findViewById(R.id.risultato3);
		TextView risultato4 = (TextView)findViewById(R.id.risultato4);
		TextView risultato5 = (TextView)findViewById(R.id.risultato5);
		TextView risultato6 = (TextView)findViewById(R.id.risultato6);
		
		
		/*IMMETTO I VALORI DEI RISULTATI NELLE CASELLE DI TESTO*/
		risultato1.setText( getString(R.string.critical_load) + new DecimalFormat("################.###").format(CC/1000) + "[kN]" );
		risultato2.setText( Html.fromHtml("<b>λ</b> : " + new DecimalFormat("################.###").format(lambda)) );
		risultato3.setText( Html.fromHtml("<b>λ</b><sub>lim</sub> : " + new DecimalFormat("################.###").format(lambda_lim)) );
		if ( C < CC )
			risultato4.setText(R.string.tozza);
		else
			risultato4.setText( getString(R.string.snella) );
		if (calculate) {
			risultato5.setText( Html.fromHtml("<b>e</b><sub>tot</sub> : " + new DecimalFormat("################.#").format(Etot) + (Double.isNaN(Etot)?"":" [mm]")) );
			risultato6.setText( Html.fromHtml("<b>M</b><sub>sd</sub> : " + new DecimalFormat("################.###").format(MSD) + (Double.isNaN(MSD/1000000)?"":" [kN*m]")) );
		} else {
			risultato5.setText("");
			risultato6.setText("");
		}
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
 			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
}
