package com.vito.gismapapp.Base;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.vito.gismapapp.Common.ToolBarHelper;

/**
 * 兰州北科维拓
 * <p>
 * FileName: BaseActivity
 * <p>
 * Author: 魏海龙
 * <p>
 * Date: 2019/10/16 17:13
 * <p>
 * Description:
 */
public class BaseActivity extends AppCompatActivity {
    private ToolBarHelper mToolBarHelper;
    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        mToolBarHelper=new ToolBarHelper(this,layoutResID);
        toolbar=mToolBarHelper.getToolBar();
        setContentView(mToolBarHelper.getContentView());
        setSupportActionBar(toolbar);
        onCreateCustomToolBar(toolbar);
    }

    public void onCreateCustomToolBar(Toolbar toolbar){
        toolbar.setContentInsetsRelative(0,0);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
