package com.rollulp.shearstress;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends ActionBarActivity {

	private EditText	etLunghezza, etNormale, etMMassimo, etMMinimo, etBase,
						etAltezza, etArmInf, etArmSup, etRCK, etPhi;
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etLunghezza		= (EditText)findViewById(R.id.etLunghezza);
		spinner			= ( Spinner)findViewById(R.id.spinner);
		etNormale		= (EditText)findViewById(R.id.etNormale);
		etMMassimo		= (EditText)findViewById(R.id.etMMassimo);
		etMMinimo		= (EditText)findViewById(R.id.etMMinimo);
		etBase			= (EditText)findViewById(R.id.etBase);
		etAltezza		= (EditText)findViewById(R.id.etAltezza);
		etArmInf		= (EditText)findViewById(R.id.etInfArmor);
		etArmSup		= (EditText)findViewById(R.id.etSupArmor);
		etRCK			= (EditText)findViewById(R.id.etRCK);
		etPhi			= (EditText)findViewById(R.id.etPhi);
	}
	
	public void calculate_results(View v) {
		
		/*ESTRAGGO TUTTO COME STRINGHE DAGLI EDITTEXT*/
		String strLenght			= new String( etLunghezza	.getText().toString() );
		String strRestrains			= Long.toString( spinner	.getSelectedItemId () );
		String strNormalForce		= new String( etNormale		.getText().toString() );
		String strMaximumMomentum	= new String( etMMassimo	.getText().toString() );
		String strMinimumMomentum	= new String( etMMinimo		.getText().toString() );
		String strBase				= new String( etBase		.getText().toString() );
		String strHeight			= new String( etAltezza		.getText().toString() );
		String strInferiorArmor		= new String( etArmInf		.getText().toString() );
		String strSuperiorArmor		= new String( etArmSup		.getText().toString() );
		String strRCK				= new String( etRCK			.getText().toString() );
		String strViscosity			= new String( etPhi			.getText().toString() );
		
		/*AZZERO I CAMPI LASCIATI VUOTI E SETTO allFields PER ALERTDIALOG E TOAST DOPO*/
		byte allFields = 0;
		if ( strLenght			.isEmpty() ) { etLunghezza	.setText( strLenght				= "0" ); allFields++; }
		if ( strNormalForce		.isEmpty() ) { etNormale	.setText( strNormalForce		= "0" ); allFields++; }
		if ( strMaximumMomentum	.isEmpty() ) { etMMassimo	.setText( strMaximumMomentum	= "0" ); allFields++; }
		if ( strMinimumMomentum	.isEmpty() ) { etMMinimo	.setText( strMinimumMomentum	= "0" ); allFields++; }
		if ( strBase			.isEmpty() ) { etBase		.setText( strBase				= "0" ); allFields++; }
		if ( strHeight			.isEmpty() ) { etAltezza	.setText( strHeight				= "0" ); allFields++; }
		if ( strInferiorArmor	.isEmpty() ) { etArmInf		.setText( strInferiorArmor		= "0" ); allFields++; }
		if ( strSuperiorArmor	.isEmpty() ) { etArmSup		.setText( strSuperiorArmor		= "0" ); allFields++; }
		if ( strRCK				.isEmpty() ) { etRCK		.setText( strRCK				= "0" ); allFields++; }
		if ( strViscosity		.isEmpty() ) { etPhi		.setText( strViscosity			= "0" ); allFields++; }
		
		/*TRASFORMO LE STRINGHE IN NUMERI*/
		double nLenght 			= 1000.00 * Double.parseDouble( strLenght			);
		double nRestrains		= 0;
		double nNormalForce		= 1000.00 * Double.parseDouble( strNormalForce		);
		double nMaximumMomentum	= 1000000 * Double.parseDouble( strMaximumMomentum	);
		double nMinimumMomentum	= 1000000 * Double.parseDouble( strMinimumMomentum	);
		double nBase			= 1.00000 * Double.parseDouble( strBase				);
		double nHeight			= 1.00000 * Double.parseDouble( strHeight			);
		double nInferiorArmor	= 1.00000 * Double.parseDouble( strInferiorArmor	);
		double nSuperiorArmor	= 1.00000 * Double.parseDouble( strSuperiorArmor	);
		double nRCK				= 1.00000 * Double.parseDouble( strRCK				);
		double nViscosity		= 1.00000 * Double.parseDouble( strViscosity		);
		switch ( Integer.parseInt(strRestrains) ) {
		case 0: nRestrains = 0.5; break; // incastro-incastro
		case 1: nRestrains = 0.7; break; // incastro-appoggio
		case 2: nRestrains = 2.0; break; // incastro-libero
		case 3: nRestrains = 1.0; break; // appoggio-appoggio
		}
		
		/*CREO LA NUOVA ATTIVITÀ E LE MANDO I DATI*/
		// La faccio partire dall' AlertDialog se esso esiste, altrimenti da fuori. Così se esiste aspetta il click per avanzare.
		final Intent intent = new Intent( getApplicationContext(), ActivityResults.class);
		intent.putExtra("data", new DataParcelable(	nLenght, nRestrains, nNormalForce, nMaximumMomentum, nMinimumMomentum, nBase,
				nHeight, nInferiorArmor, nSuperiorArmor, nRCK, nViscosity, allFields ) );
		
		/*DIALOGO POPUP*/
		if ( allFields != 0 ) {
			final AlertDialog ad = new AlertDialog.Builder(this).create();
			ad.setTitle( getString(R.string.alert_title) );
			ad.setMessage( getString(R.string.alert_message) );
			ad.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
				@Override public void onClick(DialogInterface arg0, int arg1) {
					startActivity(intent); // Inizio dal bottone se non sono stati inseriti tutti i dati
					ad.dismiss();
				}
			});
			ad.show();
		}
		else startActivity(intent); // Inizio da fuori altrimenti
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		final Intent intent;
		
try{
		switch ( item.getItemId() ) {
		case R.id.action_schema:
			intent = new Intent( getApplicationContext(), ActivityData.class);
			startActivity(intent);
			break;
		case R.id.action_info:
			intent = new Intent( getApplicationContext(), ActivityInfo.class);
			startActivity(intent);				
			break;
		case R.id.action_formule:
			intent = new Intent( getApplicationContext(), ActivityFormulas.class);
			startActivity(intent);
			break;
		case R.id.action_crediti:
			intent = new Intent( getApplicationContext(), ActivityCredits.class);
			startActivity(intent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
}catch(Exception e){
	e.printStackTrace();
	System.out.println(e.getMessage());
}
		return true;
	}
	
}
