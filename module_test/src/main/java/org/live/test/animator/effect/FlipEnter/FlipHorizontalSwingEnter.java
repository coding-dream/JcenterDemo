package org.live.test.animator.effect.FlipEnter;

import android.animation.ObjectAnimator;
import android.view.View;

import org.live.test.animator.BaseAnimatorSet;

public class FlipHorizontalSwingEnter extends BaseAnimatorSet {

	public FlipHorizontalSwingEnter() {
		duration = 1000;
	}

	@Override
	public void setAnimation(View view) {
		animatorSet.playTogether(
				ObjectAnimator.ofFloat(view, "rotationY", 90, -10, 10, 0),
				ObjectAnimator.ofFloat(view, "alpha", 0.25f, 0.5f, 0.75f, 1));
	}
}
