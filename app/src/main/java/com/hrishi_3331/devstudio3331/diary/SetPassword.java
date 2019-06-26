package com.hrishi_3331.devstudio3331.diary;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SetPassword extends AppCompatActivity {

    private EditText username;
    private EditText userpin1;
    private EditText userpin2;
    private boolean imageAttached;
    private int avtar_number;
    private ImageView user_image;
    private Avatar avatar;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        username = (EditText)findViewById(R.id.username);
        userpin1 = (EditText)findViewById(R.id.userpin);
        userpin2 = (EditText)findViewById(R.id.userpin2);

        user_image = (ImageView)findViewById(R.id.userimage);
        avtar_number = 0;
        imageAttached = false;
        cropImage();

        avatar = new Avatar();
        databaseHelper = new DatabaseHelper(SetPassword.this);
    }

    private void cropImage() {
        Drawable drawable = user_image.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
        user_image.setImageBitmap(getclip(bitmapDrawable.getBitmap()));
    }

    public static Bitmap getclip(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public void pickImage(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SetPassword.this);
        LayoutInflater inflater = getLayoutInflater();
        View DialogLayout = inflater.inflate(R.layout.image_select_dialog, null);
        builder.setView(DialogLayout);

        ImageButton mav1 = (ImageButton) DialogLayout.findViewById(R.id.male_avtar_1);
        ImageButton mav2 = (ImageButton) DialogLayout.findViewById(R.id.male_avtar_2);
        ImageButton mav3 = (ImageButton) DialogLayout.findViewById(R.id.male_avtar_3);
        ImageButton mav4 = (ImageButton) DialogLayout.findViewById(R.id.male_avtar_4);
        ImageButton mav5 = (ImageButton) DialogLayout.findViewById(R.id.male_avtar_5);
        ImageButton mav6 = (ImageButton) DialogLayout.findViewById(R.id.male_avtar_6);

        final AlertDialog Idialog = builder.create();

        mav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageAttached = true;
                avtar_number = 0;
                user_image.setImageResource(avatar.getAvtar(0));
                cropImage();
                Idialog.dismiss();
            }
        });

        mav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageAttached = true;
                avtar_number = 1;
                user_image.setImageResource(avatar.getAvtar(1));
                cropImage();
                Idialog.dismiss();
            }
        });

        mav3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageAttached = true;
                avtar_number = 2;
                user_image.setImageResource(avatar.getAvtar(2));
                cropImage();
                Idialog.dismiss();
            }
        });

        mav4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageAttached = true;
                avtar_number = 4;
                user_image.setImageResource(avatar.getAvtar(4));
                cropImage();
                Idialog.dismiss();
            }
        });

        mav5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageAttached = true;
                avtar_number = 3;
                user_image.setImageResource(avatar.getAvtar(3));
                cropImage();
                Idialog.dismiss();
            }
        });

        mav6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageAttached = true;
                avtar_number = 5;
                user_image.setImageResource(avatar.getAvtar(5));
                cropImage();
                Idialog.dismiss();
            }
        });

        Idialog.show();
    }

    public void Submit(View view){
        String name = username.getText().toString();
        String pin1 = userpin1.getText().toString();
        String pin2 = userpin2.getText().toString();

        if (name.isEmpty()){
            Toast.makeText(this, "Please Enter your name", Toast.LENGTH_SHORT).show();
        }
        else if(pin1.isEmpty()){
            Toast.makeText(this, "Please enter a pin", Toast.LENGTH_SHORT).show();
        }
        else if (pin1.length() != 4){
            Toast.makeText(this, "Pin should be 4 digit only", Toast.LENGTH_SHORT).show();
        }
        else if (!pin1.equals(pin2)){
            Toast.makeText(this, "Pin does not match. Please re-enter pin again", Toast.LENGTH_SHORT).show();
        }
        else if (!imageAttached){
            Toast.makeText(this, "Please select a avtar", Toast.LENGTH_SHORT).show();
        }
        else {
            SaveData(name, pin1, avtar_number);
        }
    }

    private void SaveData(String name, String pin1, int avtar_number) {
        SharedPreferences preferences = getSharedPreferences("mDiary", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Authorized", true);
        editor.putString("Name", name);
        editor.putString("PIN", pin1);
        editor.putInt("Avtar", avtar_number);
        editor.commit();

        try {
            FileOutputStream outputStream = openFileOutput("Welcome.txt", MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(getResources().getString(R.string.default_content));
            writer.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        java.util.Date date = calendar.getTime();
        String time = new SimpleDateFormat("h:mm a").format(date);
        String mDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        databaseHelper.AddData("Welcome User", "Welcome.txt", mDate, time);

        Intent intent = new Intent(SetPassword.this, Home.class);
        startActivity(intent);
        finish();
    }

}
