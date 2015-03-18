/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pillar/git/AndroidSamples/collusiveattack/CollusiveAttack/src/edu/ntu/security/sendinfoout/aidl/IStealInfoService.aidl
 */
package edu.ntu.security.sendinfoout.aidl;
public interface IStealInfoService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements edu.ntu.security.sendinfoout.aidl.IStealInfoService
{
private static final java.lang.String DESCRIPTOR = "edu.ntu.security.sendinfoout.aidl.IStealInfoService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an edu.ntu.security.sendinfoout.aidl.IStealInfoService interface,
 * generating a proxy if needed.
 */
public static edu.ntu.security.sendinfoout.aidl.IStealInfoService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof edu.ntu.security.sendinfoout.aidl.IStealInfoService))) {
return ((edu.ntu.security.sendinfoout.aidl.IStealInfoService)iin);
}
return new edu.ntu.security.sendinfoout.aidl.IStealInfoService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_sendInfoOut:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.sendInfoOut(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements edu.ntu.security.sendinfoout.aidl.IStealInfoService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void sendInfoOut(java.lang.String data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
mRemote.transact(Stub.TRANSACTION_sendInfoOut, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_sendInfoOut = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void sendInfoOut(java.lang.String data) throws android.os.RemoteException;
}
