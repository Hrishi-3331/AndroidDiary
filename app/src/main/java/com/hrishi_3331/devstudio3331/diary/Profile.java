package com.hrishi_3331.devstudio3331.diary;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    private ImageView userimage;
    private TextView username;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Avatar avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userimage = (ImageView)findViewById(R.id.profile_image);
        username = (TextView)findViewById(R.id.profile_name);

        preferences = getSharedPreferences("mDiary", MODE_PRIVATE);
        editor = preferences.edit();

        username.setText(preferences.getString("Name", "User"));

        avatar = new Avatar();
        userimage.setImageResource(avatar.getAvtar(preferences.getInt("Avtar", 0)));

        cropImage();
    }

    private void cropImage() {
        Drawable drawable = userimage.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
        userimage.setImageBitmap(getclip(bitmapDrawable.getBitmap()));
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

    public void Back(View view){
        finish();
    }

    public void ChangeName(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        LayoutInflater inflater = getLayoutInflater();
        View DialogLayout = inflater.inflate(R.layout.name_change_dialog, null);
        builder.setView(DialogLayout);

        Button ok = (Button) DialogLayout.findViewById(R.id.btn_ok);
        Button cancel = (Button) DialogLayout.findViewById(R.id.btn_cancel);
        final EditText name_change = (EditText)DialogLayout.findViewById(R.id.new_username);

        final AlertDialog name_change_dialog = builder.create();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_change.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(Profile.this, "Field empty", Toast.LENGTH_SHORT).show();
                }else {
                    editor.putString("Name", name);
                    editor.commit();
                    username.setText(name);
                    name_change_dialog.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_change_dialog.dismiss();
            }
        });

        name_change_dialog.show();
    }

    public void ChangeImage(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
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
                editor.putInt("Avtar", 0);
                editor.commit();
                userimage.setImageResource(avatar.getAvtar(0));
                cropImage();
                Idialog.dismiss();
            }
        });

        mav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Avtar", 1);
                editor.commit();
                userimage.setImageResource(avatar.getAvtar(1));
                cropImage();
                Idialog.dismiss();
            }
        });

        mav3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Avtar", 2);
                editor.commit();
                userimage.setImageResource(avatar.getAvtar(2));
                cropImage();
                Idialog.dismiss();
            }
        });

        mav4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Avtar", 4);
                editor.commit();
                userimage.setImageResource(avatar.getAvtar(4));
                cropImage();
                Idialog.dismiss();
            }
        });

        mav5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Avtar", 3);
                editor.commit();
                userimage.setImageResource(avatar.getAvtar(3));
                cropImage();
                Idialog.dismiss();
            }
        });

        mav6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Avtar", 5);
                editor.commit();
                userimage.setImageResource(avatar.getAvtar(5));
                cropImage();
                Idialog.dismiss();
            }
        });

        Idialog.show();
    }

    public void ChangePin(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        LayoutInflater inflater = getLayoutInflater();
        View DialogLayout = inflater.inflate(R.layout.pin_change_dialog, null);
        builder.setView(DialogLayout);

        Button ok = (Button) DialogLayout.findViewById(R.id.btn_ok);
        Button cancel = (Button) DialogLayout.findViewById(R.id.btn_cancel);
        final EditText old_pin = (EditText)DialogLayout.findViewById(R.id.old_userpin);
        final EditText new_pin = (EditText)DialogLayout.findViewById(R.id.new_userpin);
        final EditText new_pin_confirm = (EditText)DialogLayout.findViewById(R.id.new_userpin_confirm);
        final TextView warning = (TextView)DialogLayout.findViewById(R.id.warning);

        final AlertDialog pin_change_dialog = builder.create();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String OLD_PIN = old_pin.getText().toString();
               String NEW_PIN = new_pin.getText().toString();
               String NEW_PIN_CONFIRM = new_pin_confirm.getText().toString();

               old_pin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                   @Override
                   public void onFocusChange(View v, boolean hasFocus) {
                       if (hasFocus){
                           warning.setVisibility(View.GONE);
                       }
                   }
               });

               if(OLD_PIN.isEmpty()){
                   Toast.makeText(Profile.this, "Enter the old PIN to change PIN", Toast.LENGTH_SHORT).show();
               }
               else if(NEW_PIN.isEmpty()){
                   Toast.makeText(Profile.this, "Enter New PIN", Toast.LENGTH_SHORT).show();
               }
               else if (NEW_PIN_CONFIRM.isEmpty()){
                   Toast.makeText(Profile.this, "Please re-enter new PIN", Toast.LENGTH_SHORT).show();
               }
               else if (!NEW_PIN.equals(NEW_PIN_CONFIRM)){
                   Toast.makeText(Profile.this, "Re-entered PIN did not match. Please re-enter PIN correctly", Toast.LENGTH_SHORT).show();
               }
               else if(!OLD_PIN.equals(preferences.getString("PIN", "0000"))){
                    warning.setVisibility(View.VISIBLE);
               }
               else{
                   editor.putString("PIN", NEW_PIN);
                   editor.commit();
                   pin_change_dialog.dismiss();
                   Toast.makeText(Profile.this, "PIN changed successfully!", Toast.LENGTH_SHORT).show();
               }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin_change_dialog.dismiss();
            }
        });

        pin_change_dialog.show();
    }
}
