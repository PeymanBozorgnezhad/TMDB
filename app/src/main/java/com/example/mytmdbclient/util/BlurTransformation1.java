package com.example.mytmdbclient.util;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.renderscript.Allocation;
import androidx.renderscript.Element;
import androidx.renderscript.RenderScript;
import androidx.renderscript.ScriptIntrinsicBlur;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.BitmapTransformation;

public class BlurTransformation1 extends BitmapTransformation {

    private RenderScript rs;

    public BlurTransformation1(Context context) {

        rs = RenderScript.create( context );
    }



    @Override
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight)
    {
        Bitmap blurredBitmap = toTransform.copy( Bitmap.Config.ARGB_8888, true );

        // Allocate memory for Renderscript to work with
        Allocation input = Allocation.createFromBitmap(
                rs,
                blurredBitmap,
                Allocation.MipmapControl.MIPMAP_FULL,
                Allocation.USAGE_SHARED
        );
        Allocation output = Allocation.createTyped(rs, input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);

        // Set the blur radius
        script.setRadius(10);

        // Start the ScriptIntrinisicBlur
        script.forEach(output);

        // Copy the output to the blurred bitmap
        output.copyTo(blurredBitmap);

        toTransform.recycle();

        return blurredBitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest)
    {

    }

    @Override
    public boolean equals(Object o)
    {
        return false;
    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    /*@Override
    public String getId() {
        return "blur";
    }*/
}
