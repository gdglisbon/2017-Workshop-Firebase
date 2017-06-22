package pt.gdglx.a04storage;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StorageActivity extends AppCompatActivity implements IPickResult {

    @BindView(R.id.picture_result)
    ImageView pictureResult;

    private StorageReference mStorageRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.storage);
        ButterKnife.bind(this);

        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    @OnClick(R.id.take_picture)
    void takePicture()
    {
        PickSetup setup = new PickSetup().setPickTypes(EPickType.CAMERA);

        PickImageDialog.build(setup).show(this);
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            pictureResult.setImageBitmap(pickResult.getBitmap());
            saveToFirebaseStorage(pickResult.getUri());
        } else {
            Toast.makeText(this, pickResult.getError().getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    /* ************************************************************
                    GDG Firebase Workshop
     ************************************************************ */

    private void saveToFirebaseStorage(Uri file) {

        String name = String.format("%s.png",System.currentTimeMillis());

        StorageReference imageRef = mStorageRef.child("images/" + name);

        imageRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {


                    }
                });
    }
}
