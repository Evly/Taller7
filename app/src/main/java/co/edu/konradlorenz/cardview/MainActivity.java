package co.edu.konradlorenz.cardview;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SeriesAdapter adapter;
    private List<Serie> serieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        serieList = new ArrayList<>();
        adapter = new SeriesAdapter(this, serieList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareSeries();

        try {
            Glide.with(this).load(R.drawable.fondofinal).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareSeries() {
        int[] covers = new int[]{
                R.drawable.serie1,
                R.drawable.serie2,
                R.drawable.serie3,
                R.drawable.serie4,
                R.drawable.serie5,
                R.drawable.serie6,
                R.drawable.serie7,
                R.drawable.serie8,
                R.drawable.serie9,
                R.drawable.serie10
        };



        Serie EveSeries = new Serie("Lucifer", 3, covers[0],"Ejemplo serie: En este espacio va la descripción del capitulo.");
        serieList.add(EveSeries);

        EveSeries = new Serie("Riverdale", 3, covers[1],"Ejemplo serie: En este espacio va la descripción del capitulo.");
        serieList.add(EveSeries);

        EveSeries = new Serie("Orange is the New Black", 3, covers[2],"Ejemplo serie: En este espacio va la descripción del capitulo.");
        serieList.add(EveSeries);
        EveSeries = new Serie("Stranger Things", 3, covers[3],"Ejemplo serie: Ejemplo serie: En este espacio va la descripción del capitulo.");
        serieList.add(EveSeries);

        EveSeries = new Serie("La Casa de Papel", 3, covers[4],"Ejemplo serie: Ejemplo serie: En este espacio va la descripción del capitulo.");
        serieList.add(EveSeries);

        EveSeries = new Serie("The Walking Dead", 4, covers[5],"Ejemplo serie: Ejemplo serie: En este espacio va la descripción del capitulo.");
        serieList.add(EveSeries);

        EveSeries = new Serie("Los Siete Pecados Capitales", 4, covers[6],"Ejemplo serie: Ejemplo serie: En este espacio va la descripción del capitulo.");
        serieList.add(EveSeries);

        EveSeries = new Serie("Sense 8", 4, covers[7],"Ejemplo serie: Ejemplo serie: En este espacio va la descripción del capitulo.");
        serieList.add(EveSeries);

        EveSeries = new Serie("Gossip Girl", 4, covers[8],"Ejemplo serie: Ejemplo serie: En este espacio va la descripción del capitulo.");
        serieList.add(EveSeries);

        EveSeries = new Serie("Dead Note", 4, covers[9],"Ejemplo serie: Ejemplo serie: En este espacio va la descripción del capitulo.");
        serieList.add(EveSeries);


        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
