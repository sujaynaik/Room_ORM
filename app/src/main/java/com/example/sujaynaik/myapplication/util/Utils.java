package com.example.sujaynaik.myapplication.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sujaynaik.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by genora-pune on 4/5/17.
 */

public class Utils {

    private static String TAG = Utils.class.getSimpleName();

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return height;
    }

    public static void loge(String TAG, String message) {
        if (Constant.DEBUG)
            Log.e(TAG, message);
    }

    public static void loge(String TAG, String message, Object value) {
        if (Constant.DEBUG)
            Log.e(TAG, message + " " + String.valueOf(value));
    }

    public static void logd(String TAG, String message) {
        if (Constant.DEBUG)
            Log.d(TAG, message);
    }

    public static void toast(Context context, String message) {
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.toast, null);
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.setText(message);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toastAlert(Context context, String message) {
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.toast, null);
        CardView cardView = (CardView) view.findViewById(R.id.layoutCard);
        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.red));
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.setText(message);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String message) {
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.toast, null);
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.setText(message);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        listView.setNestedScrollingEnabled(true);
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            Utils.loge(TAG, "listItem null");
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                /*while (h.length() < 2)
                    h = "0" + h;*/
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getPixelsFromDPs(Context context, int dps) {
        Resources r = context.getResources();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
    }

    public static int getPixelsFromDP(Context context, int dps) {
        float d = context.getResources().getDisplayMetrics().density;
        int margin = (int) (dps * d); // margin in pixels
        return margin;
    }

    public static void hideKeyboard(Context context, View view) {
        if (view != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }/*

    public static String getMapsApiDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;

    }

    public static ArrayList<LatLng> fetchDummyPathArray() {
        ArrayList<LatLng> path_array = new ArrayList<>();

        path_array.add(new LatLng(15.478598, 73.848472));
        path_array.add(new LatLng(15.466104, 73.860236));
        path_array.add(new LatLng(15.448690, 73.862468));
        path_array.add(new LatLng(15.435225, 73.884033));
        path_array.add(new LatLng(15.415140, 73.906907));
        path_array.add(new LatLng(15.402522, 73.908044));
        path_array.add(new LatLng(15.379682, 73.925961));

        return path_array;
    }

    public static void showProfileDialog(HomeActivity mContext) {
        mProfileDialogCallbacks = mContext;
        LayoutInflater factory = LayoutInflater.from(mContext);
        View headerView = factory.inflate(R.layout.dialog_header, null);

        TypedValue outValue = new TypedValue();
        mContext.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);


        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogStyle);

        TextView dialogHeader = (TextView) headerView.findViewById(R.id.dialog_header_text);
        dialogHeader.setText("Profile Picture");

        LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textparams.setMargins(24, 0, 24, 0);
        dialogHeader.setLayoutParams(textparams);
        builder.setCustomTitle(headerView);


        final LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 100);
        //params.setMargins(24, 24, 24, 24);

        final LinearLayout uploaditemLayout = new LinearLayout(mContext);
        uploaditemLayout.setOrientation(LinearLayout.HORIZONTAL);
        uploaditemLayout.setBackgroundResource(outValue.resourceId);
        uploaditemLayout.setClickable(true);

        LinearLayout.LayoutParams picParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);


        ImageView image = new ImageView(mContext);
        image.setImageResource(R.drawable.camera);
        image.setPadding(15, 0, 15, 0);

        uploaditemLayout.addView(image, picParams);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //textParams.setMargins(24, 0, 24, 0);
        TextView text = new TextView(mContext);
        text.setText(mContext.getResources().getString(R.string.camera));
        text.setTextColor(Color.BLACK);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(15);
        uploaditemLayout.addView(text, textParams);


        layout.addView(uploaditemLayout, params);


        LinearLayout galleryitemLayout = new LinearLayout(mContext);
        galleryitemLayout.setOrientation(LinearLayout.HORIZONTAL);
        galleryitemLayout.setBackgroundResource(outValue.resourceId);
        galleryitemLayout.setClickable(true);

        picParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);


        image = new ImageView(mContext);
        image.setImageResource(R.drawable.gallery);
        image.setPadding(15, 0, 15, 0);
        galleryitemLayout.addView(image, picParams);

        textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //textParams.setMargins(24, 0, 24, 0);
        text = new TextView(mContext);
        text.setText(mContext.getResources().getString(R.string.gallery));
        text.setTextColor(Color.BLACK);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(15);

        galleryitemLayout.addView(text, textParams);

        layout.addView(galleryitemLayout, params);


        LinearLayout removeitemLayout = new LinearLayout(mContext);
        removeitemLayout.setOrientation(LinearLayout.HORIZONTAL);
        removeitemLayout.setBackgroundResource(outValue.resourceId);
        removeitemLayout.setClickable(true);

        picParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);


        image = new ImageView(mContext);
        image.setImageResource(R.drawable.no_photo);
        image.setPadding(15, 0, 15, 0);
        removeitemLayout.addView(image, picParams);

        textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //textParams.setMargins(24, 0, 24, 0);
        text = new TextView(mContext);
        text.setText(mContext.getResources().getString(R.string.remove));
        text.setTextColor(Color.BLACK);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(15);

        removeitemLayout.addView(text, textParams);

        layout.addView(removeitemLayout, params);


        builder.setView(layout);

        builder.setNegativeButton("CANCEL", null);
        final AlertDialog picdialog = builder.show();


        uploaditemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picdialog.dismiss();
                mProfileDialogCallbacks.captureImage();
            }
        });


        galleryitemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picdialog.dismiss();
                mProfileDialogCallbacks.chooseFromGallery();
            }
        });


        removeitemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picdialog.dismiss();
                mProfileDialogCallbacks.removePhoto();
            }
        });
    }*/

    public static Bitmap checkOrientation(Context mContext, Bitmap pImage, Uri uriPath) {
        //Utils.loge("ExifInteface .........", "check orientation");
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(mContext, uriPath, projection, null, null, null);
            Cursor cursor = loader.loadInBackground();

            int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            // Rotation is stored in an EXIF tag, and this tag seems to return 0 for URIs.
            // Hence, we retrieve it using an absolute path instead!
            int rotation = 0;
            String realPath = cursor.getString(column_index_data);
            if (realPath != null) {

                try {
                    ExifInterface exif = new ExifInterface(realPath);
                    rotation = (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL));


                    //Utils.loge("ExifInteface .........", "rotation =" + rotation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pImage = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uriPath);

                return rotateBitmap(pImage, rotation, new File(realPath));

            }

            // Now we can load the bitmap from the Uri, using the correct rotation.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bitmap rotateBitmap(Bitmap bmRotated, int orientation, File file) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                break;
            //return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:

                break;
        }
        try {
            bmRotated = Bitmap.createBitmap(bmRotated, 0, 0, bmRotated.getWidth(), bmRotated.getHeight(), matrix, true);
            return bmRotated;

        } catch (OutOfMemoryError e) {


            e.printStackTrace();
        }
        //Utils.loge("image ", " image out of memory");
        return null;
    }

    public static String saveToExternalStorage(Context context, Bitmap profileImage, String picName) {
        String root = Environment
                .getExternalStorageDirectory()
                + "/MyApplication" + "/ProfilePictures/";
        File myDir = new File(root);
        myDir.mkdirs();

        File file = new File(myDir, picName + ".jpg");

        FileOutputStream fo;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        try {

            file.createNewFile();

            fo = new FileOutputStream(file);
            profileImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }

    public static void performCrop(Activity mContext, String picUri, int requestCode) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            Uri contentUri;
            File f = new File(picUri);
            contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            mContext.startActivityForResult(cropIntent, requestCode);

        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private static Integer getSpacesCount(String name, int counter) {
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ') {
                counter++;
            }
        }
        Utils.loge(TAG, "getSpacesCount: counter " + counter);
        return counter;
    }

    public static boolean isValidEmail(CharSequence email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /*public static class saveUserDetails extends AsyncTask<String, String, String> {
        Context mContext;
        ProgressDialog mProgressDialog;
        String name, email, phone, password;

        public saveUserDetails(Context mContext, String name, String email, String phone, String password, ProgressDialog mProgressDialog) {
            this.mContext = mContext;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.password = password;
            this.mProgressDialog = mProgressDialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            name = name.replace(".", "");
            name = name.replaceAll("\\s{2,}", " ").trim();
            String lastName = "";
            String firstName = "";
            String finalname = name;
            int c = getSpacesCount(name, counter);
            if (c > 1) {
                String namestr = getInitials(name);
                while (c > 2) {
                    c = getSpacesCount(namestr, counter);
                    namestr = getInitials(namestr);
                }

                if (namestr.split("\\w+").length > 1) {
                    lastName = namestr.substring(namestr.lastIndexOf(" ") + 1);
                    firstName = namestr.substring(0, namestr.lastIndexOf(' '));
                } else {
                    firstName = namestr;
                }

                finalname = firstName.charAt(0) + "" + lastName.charAt(0);
            } else if (c == 1) {
                if (name.split("\\w+").length > 1) {
                    lastName = name.substring(name.lastIndexOf(" ") + 1);
                    firstName = name.substring(0, name.lastIndexOf(' '));
                } else {
                    firstName = name;
                }

                finalname = firstName.charAt(0) + "" + lastName.charAt(0);
            }
            return finalname;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressDialog.dismiss();
//            MyPreferences.saveUserDetails(mContext, "authtoken", email, name, phone, password, s);
            Intent intent = new Intent(mContext, CreditCardActivity.class);
            intent.putExtra(Constant.INTENT_FLAG, true);
            mContext.startActivity(intent);
        }
    }*/

    public static String getInitials(String name) {
        String firstName = "";
        if (name.split("\\w+").length > 1) {
            firstName = name.substring(0, name.lastIndexOf(' '));
        } else {
            firstName = name;
        }
        return firstName;
    }

    /*public static void setLocationClick(Activity activity, int request) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build(activity);
            activity.startActivityForResult(intent, request);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }*/

    public static void hideSoftKeyboard(Context activity, View view) {
        Utils.loge(TAG, "hideSoftKeyboard: ");
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        Utils.loge(TAG, "hideSoftKeyboard: ");
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getRootView().getWindowToken(), 0);
//        getWindow().getDecorView().getRootView().getWindowToken()
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    /**
     * DATE-TIME
     */
    public static String createTimestamp(Date date) {
        long date_timestamp = date.getTime();
        Utils.loge(TAG, "date_timestamp : " + date_timestamp);
        return String.valueOf(date_timestamp);
    }

    public static Date getDateFromTimestamp(String timestamp) {
        Date date = null;
        try {
            long time;
            if (timestamp.length() == 13) {
                time = Long.parseLong(timestamp);

            } else {
                time = Long.parseLong(timestamp) * 1000;
            }

            date = new Date(time);
            Utils.loge(TAG, "date : " + date);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDateTime(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date formatStringDate(String date, String pattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.parse(date);
    }

    public static String getDateTime(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void pickupTime(Context context, final View view) {
        final Calendar myCalendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String min = String.valueOf(minute);
                Utils.loge(TAG, "min : " + min + ", length : " + min.length());
                if (min.length() == 1) {
                    min = "0" + min;
                }

                if (view instanceof EditText) {
                    ((EditText) view).setText(hourOfDay + ":" + min);

                } else if (view instanceof TextView) {
                    ((TextView) view).setText(hourOfDay + ":" + min);
                }
            }
        };

        new TimePickerDialog(context, onTimeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(context)).show();
    }

    /***/
    /*public static void logout(Context context) {
        MyPreferences.clearPreferences(context);
        Intent intent = new Intent(context, SignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }*/

    /**
     * AES
     */
    public static SecretKeySpec getSecretKey() {
        // Set up secret key spec for 128-bit AES encryption and decryption
        SecretKeySpec sks = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("test_sujay".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
            Utils.loge(TAG, "sks : " + new String(kg.generateKey().getEncoded()));
        } catch (Exception e) {
            Utils.loge(TAG, "AES secret key spec error");
        }
        return sks;
    }

    public static byte[] encryptAes(SecretKeySpec secret_key, String normal_text) {
        // Encode the original data with AES
        Utils.loge(TAG, "normal_text : " + normal_text);
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, secret_key);
            encodedBytes = c.doFinal(normal_text.getBytes());
            Utils.loge(TAG, "encryptedString : " + new String(encodedBytes));
        } catch (Exception e) {
            Utils.loge(TAG, "AES encryption error");
        }
        return encodedBytes;
    }

    public static byte[] decryptAes(SecretKeySpec secret_key, byte[] encrypted_text) {
        // Decode the encoded data with AES
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, secret_key);
            decodedBytes = c.doFinal(encrypted_text);
            Utils.loge(TAG, "decryptedString : " + new String(decodedBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodedBytes;
    }

    /**
     * RSA
     */
    public static PublicKey convertPublicKey(String publicKey) {
        PublicKey public_key = null;
        try {
            byte[] key1 = publicKey.getBytes("utf-8");
            byte[] keyBytes = Base64.decode(key1, Base64.DEFAULT);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            public_key = keyFactory.generatePublic(spec);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return public_key;
    }

    public static KeyPair generateKey() {
        // Generate key pair for 1024-bit RSA encryption and decryption
        KeyPair kp = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            kp = kpg.genKeyPair();
        } catch (Exception e) {
            Utils.loge(TAG, "RSA key pair error");
        }
        return kp;
    }

    public static byte[] encodeRsa(Key privateKey, String normalText) {
        // Encode the original data with RSA private key
        Utils.loge(TAG, "privateKey : " + new String(privateKey.getEncoded()));
        Utils.loge(TAG, "normalText : " + normalText);
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, privateKey);
            encodedBytes = c.doFinal(normalText.getBytes());
            Utils.loge(TAG, "encodedBytes : " + new String(encodedBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedBytes;
    }

    public static byte[] decodeRsa(Key publicKey, byte[] encodedBytes) {
        // Decode the encoded data with RSA public key
        Utils.loge(TAG, "publicKey : " + new String(publicKey.getEncoded()));
        Utils.loge(TAG, "encodedBytes : " + new String(encodedBytes));
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, publicKey);
            decodedBytes = c.doFinal(encodedBytes);
            Utils.loge(TAG, "decodedBytes : " + new String(decodedBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodedBytes;
    }

    public static void createCircularReveal(View mainLayout, View view) {//view - previously visiblity = gone
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        // get the center for the clipping circle
        int cx = mainLayout.getWidth() / 2;
        int cy = mainLayout.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        }

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);

        if (anim != null) {
            anim.start();
        }
    }

    public static void createCircularReveal(View view) {// previously invisible view
        Utils.loge(TAG, "createCircularReveal");
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        // get the center for the clipping circle
        int cx = view.getWidth() / 2;
        int cy = view.getHeight()/* / 2*/;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);
        float startRadius = finalRadius / 2; //0

        // create the animator for this view (the start radius is zero)
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius);
        }

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);

        if (anim != null) {
            anim.start();
        }
    }

    public static void createCircularReveal2(View view) {// previously invisible view
        Utils.loge(TAG, "createCircularReveal2");
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        // get the center for the clipping circle
        int cx = view.getWidth()/* / 2*/;
        int cy = view.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);
        float startRadius = 0; //0

        // create the animator for this view (the start radius is zero)
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius);
        }

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);

        if (anim != null) {
            anim.start();
        }
    }

    public static void hideCircularReveal(final View view) {// previously visible view
        if (!Constant.DEBUG_ANIM) {
            return;
        }
        // get the center for the clipping circle
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Utils.loge(TAG, "animation start");
            // create the animation (the final radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Utils.loge(TAG, "animation");
                    view.setVisibility(View.INVISIBLE);
                }
            });

            // start the animation
            anim.start();
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void hideCircularReveal2(final View view) {// previously visible view
        if (!Constant.DEBUG_ANIM) {
            return;
        }
        // get the center for the clipping circle
        int cx = view.getWidth()/* / 2*/;
        int cy = view.getHeight() / 2;

        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Utils.loge(TAG, "animation start");
            // create the animation (the final radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Utils.loge(TAG, "animation");
                    view.setVisibility(View.INVISIBLE);
                }
            });

            // start the animation
            anim.start();
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void hideCircularReveal3(final View view) {// previously visible view
        if (!Constant.DEBUG_ANIM) {
            return;
        }
        // get the center for the clipping circle
        int cx = view.getWidth();
        int cy = view.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
            anim.setDuration(500);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                    animation.removeAllListeners();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                    view.setVisibility(View.GONE);
                    animation.removeAllListeners();
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }
            });
            // start the animation
            anim.start();

        } else {
            view.setVisibility(View.GONE);
        }
    }

    public static synchronized void animate(final Animation animation, final View view, final int duration) {
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                animation.setDuration(duration);
                animation.setInterpolator(new AnticipateOvershootInterpolator());//AccelerateDecelerateInterpolator
                animation.setFillEnabled(true);
                animation.setFillAfter(true);
                animation.setStartOffset(0);
                animation.reset();
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(animation);
            }
        };
        handler.postDelayed(runnable, 80);
    }

    public static synchronized void animate(final Animation animation, final View view, final int duration, long delayMillis) {
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                animation.setDuration(duration);
                animation.setInterpolator(new AnticipateOvershootInterpolator());//AccelerateDecelerateInterpolator
                animation.setFillEnabled(true);
                animation.setFillAfter(true);
                animation.setStartOffset(0);
                animation.reset();
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(animation);
            }
        };
        handler.postDelayed(runnable, delayMillis);
    }

    public static synchronized void animate(final Animation animation, final View view, final View parent_view, final int duration,
                                            long delayMillis) {
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            parent_view.setVisibility(View.VISIBLE);
            return;
        }
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                animation.setDuration(duration);
                animation.setInterpolator(new AnticipateOvershootInterpolator());//AccelerateDecelerateInterpolator
                animation.setFillEnabled(true);
                animation.setFillAfter(true);
                animation.setStartOffset(0);
                animation.reset();
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (parent_view instanceof CardView) {
                            Utils.createCircularReveal2(parent_view);
                        } else {
                            Utils.createCircularReveal(parent_view);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(animation);
            }
        };
        handler.postDelayed(runnable, delayMillis);
    }

    public static InputFilter setTextFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Utils.loge(TAG, "source : " + source);
                Utils.loge(TAG, "start : " + start);
                Utils.loge(TAG, "end : " + end);
                if (source != null && source.length() > 0) {
                    if (!source.toString().equals(" ")) {
                        for (int i = start; i < end; i++) {
                            Utils.loge(TAG, "i : " + i);
                            if (!Character.isLetter(source.charAt(i))) {
                                return source.subSequence(start, i);
                            }
                        }
                    }
                }
                return null;
            }
        };
    }

    public static InputFilter setPhoneFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Utils.loge(TAG, "source : " + source);
                Utils.loge(TAG, "start : " + start);
                Utils.loge(TAG, "end : " + end);
                if (source != null && source.length() > 0) {
                    if (!source.toString().equals("+")) {
                        for (int i = start; i < end; i++) {
                            Utils.loge(TAG, "i : " + i);
                            if (!Character.isDigit(source.charAt(i))) {
                                return source.subSequence(start, i);
                            }
                        }
                    }
                }
                return null;
            }
        };
    }
}
