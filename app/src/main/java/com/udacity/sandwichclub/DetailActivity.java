package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ImageView ingredientsIv;
    TextView knownAsTextView;
    TextView placeOfOriginTextView;
    TextView descriptionTextView;
    TextView ingredientsTextView;
    TextView knownAsLabel;
    TextView placeOfOriginLabel;
    TextView descriptionLabel;
    TextView ingredientsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        knownAsTextView = findViewById(R.id.also_known_tv);
        placeOfOriginTextView = findViewById(R.id.origin_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        knownAsLabel = findViewById(R.id.also_known_label);
        placeOfOriginLabel = findViewById(R.id.place_of_origin_label);
        descriptionLabel = findViewById(R.id.description_label);
        ingredientsLabel = findViewById(R.id.ingredients_label);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sandwich.getAlsoKnownAs().get(0));

            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                stringBuilder.append(", ");
                stringBuilder.append(sandwich.getAlsoKnownAs().get(i));
            }
            knownAsTextView.setText(stringBuilder.toString());
        } else {
            knownAsTextView.setVisibility(View.GONE);
            knownAsLabel.setVisibility(View.GONE);
        }
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            placeOfOriginTextView.setVisibility(View.GONE);
            placeOfOriginLabel.setVisibility(View.GONE);
        }
        if (!sandwich.getDescription().isEmpty()) {
            descriptionTextView.setText(sandwich.getDescription());
        } else {
            descriptionTextView.setVisibility(View.GONE);
            descriptionLabel.setVisibility(View.GONE);
        }
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sandwich.getIngredients().get(0));

            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                stringBuilder.append("\n");
                stringBuilder.append(sandwich.getIngredients().get(i));
            }
            ingredientsTextView.setText(stringBuilder.toString());
        } else {
            ingredientsTextView.setVisibility(View.GONE);
            ingredientsLabel.setVisibility(View.GONE);
        }

    }
}
