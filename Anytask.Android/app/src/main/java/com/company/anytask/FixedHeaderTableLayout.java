package com.company.anytask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import com.company.anytask.models.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ALL")
public class FixedHeaderTableLayout extends RelativeLayout {
    private final String HORIZONTAL_HEADERS_HORIZONTAL_SCROLL_TAG = "headerHorizontal";
    private final String CONTENT_HORIZONTAL_SCROLL_TAG = "contentHorizontal";
    private final String VERTICAL_HEADERS_VERTICAL_SCROLL_TAG = "headerVertical";
    private final String CONTENT_VERTICAL_SCROLL_TAG = "contentVertical";

    private final String TAG = FixedHeaderTableLayout.class.getName();

    private Context context;

    private TableLayout fixedTopLeftTableLayout;
    private TableLayout horizontalHeadersTableLayout;
    private TableLayout verticalHeadersTableLayout;
    private TableLayout contentTableLayout;

    private HorizontalScrollView horizontalHeadersHorizontalScrollView;
    private HorizontalScrollView contentHorizontalScrollView;

    private ScrollView verticalHeadersVerticalScrollView;
    private ScrollView contentVerticalScrollView;

    private CellItem[] horizontalHeaders;
    private Integer[] horizontalHeadersWidth;
    private CellItem[] verticalHeaders;

    public FixedHeaderTableLayout(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.context = context;
    }

    public FixedHeaderTableLayout(Context context) {
        super(context);
        this.context = context;
    }

    public void setTableContent(CellItem[] horizontalHeaders, CellItem[] verticalHeaders,
                                ArrayList<ArrayList<CellItem>> items) {
        removeAllViews();
        this.verticalHeaders = verticalHeaders;
        this.horizontalHeaders = horizontalHeaders;

        initComponents();
        setComponentsId();
        setTags();
        configureComponents();
        addComponentsToMainLayout();

        fillFixedTopLeftTableLayout();
        fillHorizontalHeadersTableLayout();
        adjustFixedTopLeftAndHorizontalTableLayoutHeights();

        horizontalHeadersWidth = getHorizontalHeadersWidth();
        fillVerticalHeadersTableLayout();
        fillContentTableLayout(items);
        adjustVerticalHeadersAndContentHeights();

        adjustVerticalHeaderAndFixedTopLeftWidths();
    }

    private void adjustVerticalHeaderAndFixedTopLeftWidths() {
        TableRow fixedTopLeftTableRow = (TableRow) fixedTopLeftTableLayout.getChildAt(0);
        TableRow verticalHeaderTableRow = (TableRow) verticalHeadersTableLayout.getChildAt(0);

        int fixedTopLeftWidth = getViewWidth(fixedTopLeftTableRow);
        int verticalHeaderWidth = getMaxVerticalHeaderWidth();

        TableRow tableRow = fixedTopLeftWidth < verticalHeaderWidth
                ? fixedTopLeftTableRow
                : verticalHeaderTableRow;
        int finalWidth = fixedTopLeftWidth > verticalHeaderWidth
                ? fixedTopLeftWidth
                : verticalHeaderWidth;

        adjustLayoutWidth(tableRow, finalWidth);
    }

    private int getMaxVerticalHeaderWidth() {
        int childCount = verticalHeadersTableLayout.getChildCount();
        int maxWidth = -1;

        for (int i = 0; i < childCount; i++) {
            TableRow tableRow = (TableRow) verticalHeadersTableLayout.getChildAt(i);
            int tableRowWidth = getViewWidth(tableRow);
            if (tableRowWidth > maxWidth)
                maxWidth = tableRowWidth;
        }
        return maxWidth;
    }

    private void adjustLayoutWidth(TableRow tableRow, int width) {
        int tableRowChildCount = tableRow.getChildCount();

        for (int i = 0; i < tableRowChildCount; i++) {
            View view = tableRow.getChildAt(i);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.width = width - (params.leftMargin + params.rightMargin);
        }
    }

    private void configureComponents() {
        fixedTopLeftTableLayout.setBackgroundColor(Color.GREEN);
        horizontalHeadersHorizontalScrollView.setBackgroundColor(Color.LTGRAY);
        horizontalHeadersHorizontalScrollView.setHorizontalScrollBarEnabled(false);
        contentHorizontalScrollView.setHorizontalScrollBarEnabled(false);
        verticalHeadersVerticalScrollView.setVerticalScrollBarEnabled(false);

        horizontalHeadersHorizontalScrollView.addView(horizontalHeadersTableLayout);
        verticalHeadersVerticalScrollView.addView(verticalHeadersTableLayout);
        contentVerticalScrollView.addView(contentHorizontalScrollView);
        contentHorizontalScrollView.addView(contentTableLayout);
    }

    private void initComponents() {
        fixedTopLeftTableLayout = new TableLayout(context);
        horizontalHeadersTableLayout = new TableLayout(context);
        verticalHeadersTableLayout = new TableLayout(context);
        contentTableLayout = new TableLayout(context);

        horizontalHeadersHorizontalScrollView = new MyHorizontalScrollView(context);
        contentHorizontalScrollView = new MyHorizontalScrollView(context);

        verticalHeadersVerticalScrollView = new MyScrollView(context);
        contentVerticalScrollView = new MyScrollView(context);
    }

    private void setComponentsId() {
        this.fixedTopLeftTableLayout.setId(1);
        this.horizontalHeadersHorizontalScrollView.setId(2);
        this.verticalHeadersVerticalScrollView.setId(3);
        this.contentVerticalScrollView.setId(4);
    }

    private void setTags() {
        horizontalHeadersHorizontalScrollView.setTag(HORIZONTAL_HEADERS_HORIZONTAL_SCROLL_TAG);
        contentHorizontalScrollView.setTag(CONTENT_HORIZONTAL_SCROLL_TAG);
        verticalHeadersVerticalScrollView.setTag(VERTICAL_HEADERS_VERTICAL_SCROLL_TAG);
        contentVerticalScrollView.setTag(CONTENT_VERTICAL_SCROLL_TAG);
    }

    private void addComponentsToMainLayout() {
        LayoutParams horizontalHeadersLayoutParams = getWrapContentLayoutParams();
        horizontalHeadersLayoutParams.addRule(RelativeLayout.RIGHT_OF, fixedTopLeftTableLayout.getId());

        LayoutParams verticalHeadersLayoutParams = getWrapContentLayoutParams();
        verticalHeadersLayoutParams.addRule(RelativeLayout.BELOW, fixedTopLeftTableLayout.getId());

        LayoutParams contentLayoutParams = getWrapContentLayoutParams();
        contentLayoutParams.addRule(RelativeLayout.RIGHT_OF, verticalHeadersVerticalScrollView.getId());
        contentLayoutParams.addRule(RelativeLayout.BELOW, horizontalHeadersHorizontalScrollView.getId());

        addView(fixedTopLeftTableLayout);
        addView(horizontalHeadersHorizontalScrollView, horizontalHeadersLayoutParams);
        addView(verticalHeadersVerticalScrollView, verticalHeadersLayoutParams);
        addView(contentVerticalScrollView, contentLayoutParams);
    }

    private LayoutParams getWrapContentLayoutParams() {
        return new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
    }

    private void fillFixedTopLeftTableLayout() {
        fixedTopLeftTableLayout.addView(createFixedTopLeftTableRow());
    }

    private void fillHorizontalHeadersTableLayout() {
        horizontalHeadersTableLayout.addView(createHorizontalHeadersTableRow());
    }

    private TableRow createFixedTopLeftTableRow() {
        TableRow tableRow = new TableRow(context);
        TextView textView = createHeaderTextView(new CellItem(null, 0, Status.BLANK, ""));
        tableRow.addView(textView);

        return tableRow;
    }

    private TableRow createHorizontalHeadersTableRow() {
        TableRow tableRow = new TableRow(context);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT
        );
        params.setMargins(2, 0, 0, 0);

        for (CellItem horizontalHeader : horizontalHeaders) {
            TextView textView = createHeaderTextView(horizontalHeader);
            textView.setLayoutParams(params);
            tableRow.addView(textView);
        }

        return tableRow;
    }

    private void fillVerticalHeadersTableLayout() {
        for (CellItem item : verticalHeaders) {
            verticalHeadersTableLayout.addView(createVerticalHeadersTableRow(item));
        }
    }

    private void fillContentTableLayout(ArrayList<ArrayList<CellItem>> items) {
        for (List<CellItem> item : items) {
            TableRow tableRow = createContentTableRow(item);
            tableRow.setBackgroundColor(Color.LTGRAY);
            contentTableLayout.addView(tableRow);
        }
    }

    private TableRow createVerticalHeadersTableRow(CellItem cell) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                this.horizontalHeadersWidth[0],
                LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, 2, 0, 0);

        TableRow tableRow = new TableRow(this.context);
        TextView textView = createContentTextView(cell);
        tableRow.addView(textView, params);

        return tableRow;
    }

    private TableRow createContentTableRow(List<CellItem> cells) {
        TableRow tableRow = new TableRow(this.context);
        int loopCount = horizontalHeaders.length;

        for (int i = 0; i < loopCount; i++) {
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    horizontalHeadersWidth[i],
                    LayoutParams.MATCH_PARENT
            );
            params.setMargins(2, 2, 0, 0);

            TextView textViewB = createContentTextView(cells.get(i));
            tableRow.addView(textViewB, params);
        }

        return tableRow;
    }



    private TextView createContentTextView(final CellItem item) {
        HashMap<Status, Integer> colors = new HashMap<Status, Integer>() {{
            put(Status.BLANK, Color.WHITE);
            put(Status.NEW, Color.GRAY);
            put(Status.COMPLETE, Color.GREEN);
            put(Status.INFO_REQUIRED, Color.CYAN);
            put(Status.ON_REWORK, Color.RED);
            put(Status.ON_REVIEW, Color.YELLOW);
        }};

        TextView textView = new TextView(context);
        if (item.taskId != 0) {
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentActivity.class)
                            .putExtra("userId", item.userId)
                            .putExtra("taskId", item.taskId)
                            .putExtra(Intent.EXTRA_TEXT, item.text);
                    context.startActivity(intent);
                }
            });
        }
        textView.setBackgroundColor(colors.get(item.status));
        textView.setText(item.text);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(5, 5, 5, 5);
        return textView;
    }

    private TextView createHeaderTextView(CellItem item) {
        TextView headerTextView = new TextView(this.context);
        headerTextView.setText(item.text);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setPadding(5, 5, 5, 5);
        headerTextView.setBackgroundColor(Color.CYAN);
        return headerTextView;
    }

    private void adjustFixedTopLeftAndHorizontalTableLayoutHeights() {
        TableRow fixedTopLeftTableRow = (TableRow) fixedTopLeftTableLayout.getChildAt(0);
        TableRow horizontalHeaderTableRow = (TableRow) horizontalHeadersTableLayout.getChildAt(0);

        int fixedTopLeftHeight = getViewHeight(fixedTopLeftTableRow);
        int horizontalHeaderHeight = getViewHeight(horizontalHeaderTableRow);

        TableRow tableRow = fixedTopLeftHeight < horizontalHeaderHeight
                ? fixedTopLeftTableRow
                : horizontalHeaderTableRow;
        int finalHeight = fixedTopLeftHeight > horizontalHeaderHeight
                ? fixedTopLeftHeight
                : horizontalHeaderHeight;

        adjustLayoutHeight(tableRow, finalHeight);
    }

    private Integer[] getHorizontalHeadersWidth() {
        Integer[] headerCellsWidth = new Integer[horizontalHeaders.length];
        for (int i = 0; i < horizontalHeaders.length; i++) {
            headerCellsWidth[i] = getViewWidth(((TableRow) horizontalHeadersTableLayout.getChildAt(0)).getChildAt(i));
        }
        return headerCellsWidth;
    }

    private void adjustVerticalHeadersAndContentHeights() {
        for (int i = 0; i < verticalHeaders.length; i++) {
            TableRow verticalHeaderTableRow = (TableRow) verticalHeadersTableLayout.getChildAt(i);
            TableRow contentTableRow = (TableRow) contentTableLayout.getChildAt(i);

            int verticalHeaderTableRowHeight = getViewHeight(verticalHeaderTableRow);
            int contentTableRowHeight = getViewHeight(contentTableRow);

            TableRow tableRow = verticalHeaderTableRowHeight < contentTableRowHeight
                    ? verticalHeaderTableRow
                    : contentTableRow;
            int finalHeight = verticalHeaderTableRowHeight > contentTableRowHeight
                    ? verticalHeaderTableRowHeight
                    : contentTableRowHeight;

            adjustLayoutHeight(tableRow, finalHeight);
        }
    }

    private void adjustLayoutHeight(TableRow tableRow, int height) {
        int tableRowChildCount = tableRow.getChildCount();

        for (int i = 0; i < tableRowChildCount; i++) {
            View view = tableRow.getChildAt(i);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.height = height - (params.bottomMargin + params.topMargin);
        }
    }

    private int getViewHeight(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    private int getViewWidth(View view) {
        view.measure(MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    private class MyHorizontalScrollView extends HorizontalScrollView {
        public MyHorizontalScrollView(Context context) {
            super(context);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) getTag();

            if (tag.equals(HORIZONTAL_HEADERS_HORIZONTAL_SCROLL_TAG)) {
                contentHorizontalScrollView.scrollTo(l, 0);
            } else {
                horizontalHeadersHorizontalScrollView.scrollTo(l, 0);
            }
        }
    }

    private class MyScrollView extends ScrollView {
        public MyScrollView(Context context) {
            super(context);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) getTag();

            if (tag.equals(VERTICAL_HEADERS_VERTICAL_SCROLL_TAG)) {
                contentVerticalScrollView.scrollTo(0, t);
            } else {
                verticalHeadersVerticalScrollView.scrollTo(0, t);
            }
        }
    }
}

