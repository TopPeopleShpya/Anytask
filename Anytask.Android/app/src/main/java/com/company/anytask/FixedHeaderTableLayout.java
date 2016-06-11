package com.company.anytask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FixedHeaderTableLayout extends RelativeLayout {
    private final String HORIZONTAL_SCROLL_VIEW_B_TAG = "HorizontalScrollViewB";
    private final String HORIZONTAL_SCROLL_VIEW_D_TAG = "HorizontalScrollViewD";
    private final String SCROLL_VIEW_C_TAG = "ScrollViewC";
    private final String SCROLL_VIEW_D_TAG = "ScrollViewD";

    private final String TAG = FixedHeaderTableLayout.class.getName();

    private Context context;

    private TableLayout tableA;
    private TableLayout tableB;
    private TableLayout tableC;
    private TableLayout tableD;

    private HorizontalScrollView horizontalScrollViewB;
    private HorizontalScrollView horizontalScrollViewD;

    private ScrollView scrollViewC;
    private ScrollView scrollViewD;

    private CellItem[] headers;
    private int[] headerCellsWidth;

    public FixedHeaderTableLayout(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.context = context;
    }

    public FixedHeaderTableLayout(Context context) {
        super(context);
        this.context = context;
    }

    public void setTableContent(CellItem[] headers, ArrayList<ArrayList<CellItem>> items) {
        removeAllViews();
        this.headers = headers;
        headerCellsWidth = new int[headers.length];

        initComponents();
        setComponentsId();
        setScrollViewAndHorizontalScrollViewTag();

        horizontalScrollViewB.addView(tableB);
        scrollViewC.addView(tableC);
        scrollViewD.addView(horizontalScrollViewD);
        horizontalScrollViewD.addView(tableD);

        addComponentToMainLayout();
        setBackgroundColor(Color.RED);

        addTableRowToTableA();
        addTableRowToTableB();
        resizeHeaderHeight();
        getTableRowHeaderCellWidth();
        generateTableC_AndTable_B(items);
        resizeBodyTableRowHeight();
    }

    private void initComponents() {
        tableA = new TableLayout(context);
        tableB = new TableLayout(context);
        tableC = new TableLayout(context);
        tableD = new TableLayout(context);

        horizontalScrollViewB = new MyHorizontalScrollView(context);
        horizontalScrollViewD = new MyHorizontalScrollView(context);

        scrollViewC = new MyScrollView(context);
        scrollViewD = new MyScrollView(context);

        tableA.setBackgroundColor(Color.GREEN);
        horizontalScrollViewB.setBackgroundColor(Color.LTGRAY);
    }

    private void setComponentsId() {
        this.tableA.setId(1);
        this.horizontalScrollViewB.setId(2);
        this.scrollViewC.setId(3);
        this.scrollViewD.setId(4);
    }

    private void setScrollViewAndHorizontalScrollViewTag(){
        horizontalScrollViewB.setTag(HORIZONTAL_SCROLL_VIEW_B_TAG);
        horizontalScrollViewD.setTag(HORIZONTAL_SCROLL_VIEW_D_TAG);
        scrollViewC.setTag(SCROLL_VIEW_C_TAG);
        scrollViewD.setTag(SCROLL_VIEW_D_TAG);
    }

    private void addComponentToMainLayout(){
        RelativeLayout.LayoutParams bParams = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        );
        bParams.addRule(RelativeLayout.RIGHT_OF, tableA.getId());

        RelativeLayout.LayoutParams cParams = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        );
        cParams.addRule(RelativeLayout.BELOW, tableA.getId());

        RelativeLayout.LayoutParams dParams = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        );
        dParams.addRule(RelativeLayout.RIGHT_OF, scrollViewC.getId());
        dParams.addRule(RelativeLayout.BELOW, horizontalScrollViewB.getId());

        addView(tableA);
        addView(horizontalScrollViewB, bParams);
        addView(scrollViewC, cParams);
        addView(scrollViewD, dParams);
    }

    private void addTableRowToTableA(){
        tableA.addView(componentATableRow());
    }

    private void addTableRowToTableB(){
        tableB.addView(componentBTableRow());
    }

    private TableRow componentATableRow(){
        TableRow componentATableRow = new TableRow(context);
        TextView textView = headerTextView(headers[0]);
        componentATableRow.addView(textView);

        return componentATableRow;
    }

    private TableRow componentBTableRow(){
        TableRow componentBTableRow = new TableRow(context);
        int headerFieldCount = headers.length;

        TableRow.LayoutParams params = new TableRow.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        );
        params.setMargins(2, 0, 0, 0);

        for (int i = 0; i < headerFieldCount - 1; i++) {
            TextView textView = headerTextView(headers[i + 1]);
            textView.setLayoutParams(params);
            componentBTableRow.addView(textView);
        }

        return componentBTableRow;
    }

    private void generateTableC_AndTable_B(ArrayList<ArrayList<CellItem>> items) {
        for(List<CellItem> item : items){
            TableRow tableRowForTableC = tableRowForTableC(item);
            TableRow taleRowForTableD = taleRowForTableD(item);

            tableRowForTableC.setBackgroundColor(Color.LTGRAY);
            taleRowForTableD.setBackgroundColor(Color.LTGRAY);

            tableC.addView(tableRowForTableC);
            tableD.addView(taleRowForTableD);
        }
    }

    private TableRow tableRowForTableC(List<CellItem> cells){
        TableRow.LayoutParams params = new TableRow.LayoutParams(
            this.headerCellsWidth[0],
            LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, 2, 0, 0);

        TableRow tableRowForTableC = new TableRow(this.context);
        TextView textView = this.bodyTextView(cells.get(0));
        tableRowForTableC.addView(textView,params);

        return tableRowForTableC;
    }

    private TableRow taleRowForTableD(List<CellItem> cells){
        TableRow taleRowForTableD = new TableRow(this.context);
        int loopCount = ((TableRow)this.tableB.getChildAt(0)).getChildCount();

        for (int i = 0 ; i < loopCount; i++) {
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                headerCellsWidth[i + 1],
                LayoutParams.MATCH_PARENT
            );
            params.setMargins(2, 2, 0, 0);

            TextView textViewB = this.bodyTextView(cells.get(i + 1));
            taleRowForTableD.addView(textViewB,params);
        }

        return taleRowForTableD;
    }

    private TextView bodyTextView(final CellItem item){
        TextView bodyTextView = new TextView(this.context);
        bodyTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class).putExtra(Intent.EXTRA_TEXT, item.text);
                context.startActivity(intent);
            }
        });
        bodyTextView.setBackgroundColor(Color.WHITE);
        bodyTextView.setText(item.text);
        bodyTextView.setGravity(Gravity.CENTER);
        bodyTextView.setPadding(5, 5, 5, 5);
        return bodyTextView;
    }

    private TextView headerTextView(CellItem item){
        TextView headerTextView = new TextView(this.context);
        headerTextView.setBackgroundColor(Color.WHITE);
        headerTextView.setText(item.text);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setPadding(5, 5, 5, 5);
        headerTextView.setBackgroundColor(Color.CYAN);
        return headerTextView;
    }

    private void resizeHeaderHeight() {
        TableRow productNameHeaderTableRow = (TableRow) tableA.getChildAt(0);
        TableRow productInfoTableRow = (TableRow) tableB.getChildAt(0);

        int rowAHeight = viewHeight(productNameHeaderTableRow);
        int rowBHeight = viewHeight(productInfoTableRow);

        TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
        int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;

        matchLayoutHeight(tableRow, finalHeight);
    }

    private void getTableRowHeaderCellWidth() {
        int tableAChildCount = ((TableRow)tableA.getChildAt(0)).getChildCount();
        int tableBChildCount = ((TableRow)tableB.getChildAt(0)).getChildCount();

        for (int i = 0; i < tableAChildCount + tableBChildCount; i++) {
            if (i == 0) {
                headerCellsWidth[i] = viewWidth(((TableRow)tableA.getChildAt(0)).getChildAt(i));
            } else {
                headerCellsWidth[i] = viewWidth(((TableRow)tableB.getChildAt(0)).getChildAt(i-1));
            }
        }
    }

    private void resizeBodyTableRowHeight() {
        int childCount = tableC.getChildCount();

        for (int i = 0; i < childCount; i++) {
            TableRow productNameHeaderTableRow = (TableRow) tableC.getChildAt(i);
            TableRow productInfoTableRow = (TableRow)  tableD.getChildAt(i);

            int rowAHeight = viewHeight(productNameHeaderTableRow);
            int rowBHeight = viewHeight(productInfoTableRow);

            TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
            int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;

            matchLayoutHeight(tableRow, finalHeight);
        }
    }

    private void matchLayoutHeight(TableRow tableRow, int height) {
        int tableRowChildCount = tableRow.getChildCount();

        if(tableRowChildCount == 1) {
            View view = tableRow.getChildAt(0);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.height = height - (params.bottomMargin + params.topMargin);
            return;
        }

        for (int i = 0; i < tableRowChildCount; i++) {
            View view = tableRow.getChildAt(i);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            if (!isTheHeighestLayout(tableRow, i)) {
                params.height = height - (params.bottomMargin + params.topMargin);
                return;
            }
        }
    }

    private boolean isTheHeighestLayout(TableRow tableRow, int layoutPosition) {
        int tableRowChildCount = tableRow.getChildCount();
        int heighestViewPosition = -1;
        int viewHeight = 0;

        for (int i = 0; i < tableRowChildCount; i++) {
            View view = tableRow.getChildAt(i);
            int height = viewHeight(view);

            if (viewHeight < height) {
                heighestViewPosition = i;
                viewHeight = height;
            }
        }

        return heighestViewPosition == layoutPosition;
    }

    private int viewHeight(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    private int viewWidth(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    class MyHorizontalScrollView extends HorizontalScrollView{
        public MyHorizontalScrollView(Context context) {
            super(context);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) getTag();

            if (tag.equals(HORIZONTAL_SCROLL_VIEW_B_TAG)) {
                horizontalScrollViewD.scrollTo(l, 0);
            } else {
                horizontalScrollViewB.scrollTo(l, 0);
            }
        }
    }

    class MyScrollView extends ScrollView {
        public MyScrollView(Context context) {
            super(context);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) getTag();

            if (tag.equals(SCROLL_VIEW_C_TAG)) {
                scrollViewD.scrollTo(0, t);
            } else {
                scrollViewC.scrollTo(0,t);
            }
        }
    }
}

class CellItem {
    public String text;

    public CellItem(String text) {
        this.text = text;
    }
}