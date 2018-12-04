package org.live.test.animator.effect.FlipExit;

import android.animation.ObjectAnimator;
import android.view.View;

import org.live.test.animator.BaseAnimatorSet;

public class FlipVerticalExit extends BaseAnimatorSet {

	@Override
	public void setAnimation(View view) {
		animatorSet.playTogether(ObjectAnimator.ofFloat(view, "rotationX", 0, 90),
				ObjectAnimator.ofFloat(view, "alpha", 1, 0));
	}
}
