package com.rollulp.shearstress;

import android.os.Parcel;
import android.os.Parcelable;

public class DataParcelable implements Parcelable {


	final public double	lenght, restrains, normalForce, maximumMomentum, minimumMomentum,
					base, height, inferiorArmor, superiorArmor, RCK, viscosity;
	final public byte allFields;
	
	
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		arg0.writeDouble(lenght);
		arg0.writeDouble(restrains);
		arg0.writeDouble(normalForce);
		arg0.writeDouble(maximumMomentum);
		arg0.writeDouble(minimumMomentum);
		arg0.writeDouble(base);
		arg0.writeDouble(height);
		arg0.writeDouble(inferiorArmor);
		arg0.writeDouble(superiorArmor);
		arg0.writeDouble(RCK);
		arg0.writeDouble(viscosity);
		arg0.writeByte(allFields);
	}
	public DataParcelable(Parcel str) {
		this.lenght				= str.readDouble();
		this.restrains			= str.readDouble();
		this.normalForce		= str.readDouble();
		this.maximumMomentum	= str.readDouble();
		this.minimumMomentum	= str.readDouble();
		this.base				= str.readDouble();
		this.height				= str.readDouble();
		this.inferiorArmor		= str.readDouble();
		this.superiorArmor		= str.readDouble();
		this.RCK				= str.readDouble();
		this.viscosity			= str.readDouble();
		this.allFields			= str.readByte();
	}
	public DataParcelable(double lenght, double restrains, double normalForce,
			double maximumMomentum, double minimumMomentum, double base, double height,
			double inferiorArmor, double superiorArmor, double RCK, double viscosity, byte allFields) {
		this.lenght				= lenght;
		this.restrains			= restrains;
		this.normalForce		= normalForce;
		this.maximumMomentum	= maximumMomentum;
		this.minimumMomentum	= minimumMomentum;
		this.base				= base;
		this.height				= height;
		this.inferiorArmor		= inferiorArmor;
		this.superiorArmor		= superiorArmor;
		this.RCK				= RCK;
		this.viscosity			= viscosity;
		this.allFields			= allFields;
	}
	
	
	@Override
	public int describeContents() { return 0; }
	public static final Creator<DataParcelable> CREATOR = new Creator<DataParcelable>() {
	    public DataParcelable	createFromParcel(Parcel in)	{ return new DataParcelable(in);	}
	    public DataParcelable[] newArray(int size)			{ return new DataParcelable[size];	}
	};

}
