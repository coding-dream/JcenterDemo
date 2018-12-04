package org.live.test.animator.effect.FadeExit;

import android.animation.ObjectAnimator;
import android.view.View;

import org.live.test.animator.BaseAnimatorSet;

public class FadeExit extends BaseAnimatorSet {

	@Override
	public void setAnimation(View view) {
		animatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration(duration));
	}
}
