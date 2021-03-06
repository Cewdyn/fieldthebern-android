/*
 * Copyright (c) 2016 - Bernie 2016, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.berniesanders.fieldthebern.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.berniesanders.fieldthebern.R;
import com.berniesanders.fieldthebern.controllers.ActionBarService;
import com.berniesanders.fieldthebern.mortar.DaggerService;
import com.berniesanders.fieldthebern.mortar.HandlesBack;
import com.berniesanders.fieldthebern.screens.PhotoScreen;
import javax.inject.Inject;
import timber.log.Timber;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 *
 */
public class PhotoScreenView extends FrameLayout implements HandlesBack {

  @Inject
  PhotoScreen.Presenter presenter;
  PhotoViewAttacher attacher;

  @Bind(R.id.tv_current_matrix)
  TextView sourceTextView;

  public PhotoScreenView(Context context) {
    super(context);
    injectSelf(context);
  }

  public PhotoScreenView(Context context, AttributeSet attrs) {
    super(context, attrs);
    injectSelf(context);
  }

  public PhotoScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    injectSelf(context);
  }

  private void injectSelf(Context context) {
    DaggerService.<PhotoScreen.Component>getDaggerComponent(context,
        DaggerService.DAGGER_SERVICE).inject(this);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    Timber.v("onFinishInflate");
    ButterKnife.bind(this, this);
    attacher = new PhotoViewAttacher(getImageView());
    attacher.setScaleType(ImageView.ScaleType.CENTER_CROP);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    presenter.takeView(this);
  }

  @Override
  protected void onDetachedFromWindow() {
    presenter.dropView(this);
    if (attacher != null) {
      attacher.cleanup();
      attacher = null;
    }
    super.onDetachedFromWindow();
  }

  public ImageView getImageView() {
    return (ImageView) findViewById(R.id.iv_photo);
  }

  public TextView getSourceTextView() {
    return sourceTextView;
  }

  @Override
  public boolean onBackPressed() {
    if (attacher != null) {
      attacher.cleanup();
      attacher = null;
    }

    ActionBarService.get(this).showToolbar();
    return false;
  }
}
