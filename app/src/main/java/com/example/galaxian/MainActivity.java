package com.example.galaxian;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addPlayer = findViewById(R.id.button);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameValue = findViewById(R.id.editText);
                EditText scoreValue = findViewById(R.id.editText2);

                String name = nameValue.getText().toString();
                String scoreText = scoreValue.getText().toString();

                if (name.isEmpty() || scoreText.isEmpty()) {
                    Toast.makeText(MainActivity.this,"Empty line", Toast.LENGTH_SHORT).show();
                }
                else {
                    int score = Integer.parseInt(scoreText);
                    player players = new player(name,score);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference playerRef = database.getReference("player");
                    String key = playerRef.push().getKey();
                    playerRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Failed to read value.", Toast.LENGTH_LONG).show();
                        }
                    });
                    playerRef.child(key).setValue(players);
                }
            }
        });

        Button BestPlayer = findViewById(R.id.ViewBestGamer);
        BestPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference playerRef = database.getReference("player");
        playerRef.orderByChild("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    player student = studentSnapshot.getValue(player.class);
                    Toast.makeText(MainActivity.this, "name : " + student.getName() + "  " + " score : " + student.getScore(),
                            Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


            }
        });

    }
}
