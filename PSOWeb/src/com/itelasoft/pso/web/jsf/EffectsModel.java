/* MPL License text (see http://www.mozilla.org/MPL/) */

package com.itelasoft.pso.web.jsf;

import java.util.Collection;
import java.util.HashMap;

import com.icesoft.faces.context.effects.Appear;
import com.icesoft.faces.context.effects.BlindDown;
import com.icesoft.faces.context.effects.BlindUp;
import com.icesoft.faces.context.effects.DropOut;
import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.EffectQueue;
import com.icesoft.faces.context.effects.Fade;
import com.icesoft.faces.context.effects.Highlight;
import com.icesoft.faces.context.effects.Puff;
import com.icesoft.faces.context.effects.Pulsate;
import com.icesoft.faces.context.effects.Shake;
import com.icesoft.faces.context.effects.Shrink;
import com.icesoft.faces.context.effects.SlideDown;
import com.icesoft.faces.context.effects.SlideUp;

/**
 * <p>The EffectsModel bean is stores a Map of Effect objects.  This Map
 * is used to display the list of available effects to the user as well as
 * stores effect currently selected by the user.</p>
 *
 * @since 1.7
 */
public class EffectsModel  {
	
	private boolean visible=false;
    // current effect that consists of a label and the actual Effect.
    private EffectWrapper currentEffectWrapper;

    // map of possible effects.
    private HashMap effects;

    /**
     * Creates a new instance of the effects model.
     */
    public EffectsModel() {
        init();
    }

    /**
     * Initialized all the effects object.  Effects have to be in either
     * Session or Request scope so that only the indended users sees them.
     */
    protected void init() {
        // build a list of our know effects
        effects = new HashMap(11);

        // fade appear effect combo
        EffectQueue fadeAppear = new EffectQueue("effectFadeAppear");
        fadeAppear.add(new Fade());
        fadeAppear.add(new Appear());
        effects.put("effectFadeAppear",
                new EffectWrapper(
                        "page.effect.appearFade.title",
                        fadeAppear));

        effects.put("effectHighlight",
                new EffectWrapper(
                        "page.effect.highlight.title",
                        new Highlight("#fda505")));
        // pulsate
        Pulsate pulsate = new Pulsate();
        pulsate.setDuration(1.0f);
        effects.put("effectPulsate",
                new EffectWrapper(
                        "page.effect.pulsate.title",
                        pulsate));

        // effect move and move back.
//        EffectQueue move = new EffectQueue("effectMove");
//        move.add(new Move(25, 25, "relative"));
//        move.add(new Move(-25, -25, "relative"));
//        effects.put("effectMove",
//                new EffectWrapper(
//                        "page.effect.move.title",
//                        move));

        // scale effect
//        EffectQueue scale = new EffectQueue("effectScale");
//        scale.add(new Scale(50));
//        scale.add(new Scale(200));
//        effects.put("effectScale",
//                new EffectWrapper(
//                        "page.effect.scale.title",
//                        scale));

        // puff effect
        EffectQueue puff = new EffectQueue("effectPuff");
        puff.add(new Puff());
        puff.add(new Appear());
        effects.put("effectPuff",
                new EffectWrapper(
                        "page.effect.puff.title",
                        puff));

        // Blind effect
        EffectQueue blind = new EffectQueue("effectBlind");
        blind.add(new BlindUp());
        blind.add(new BlindDown());
        effects.put("effectBlind",
                new EffectWrapper(
                        "page.effect.blind.title",
                        blind));
        
        effects.put("effectBlindDown",
                new EffectWrapper(
                        "page.effect.blind.title",
                        new BlindDown()));

        // drop out effect
        EffectQueue dropOut = new EffectQueue("effectDropOut");
        dropOut.add(new DropOut());
        dropOut.add(new Appear());
        effects.put("effectDropOut",
                new EffectWrapper(
                        "page.effect.dropout.title",
                        dropOut));

        // shake effect
        effects.put("effectShake",
                new EffectWrapper(
                        "page.effect.shake.title",
                        new Shake()));
        effects.put("effectSlideDown",
                new EffectWrapper(
                        "page.effect.shake.title",
                        new SlideDown()));

        // Slide effect
        EffectQueue slide = new EffectQueue("effectSlide");
        slide.add(new SlideUp());
        slide.add(new SlideDown());
        effects.put("effectSlide",
                new EffectWrapper(
                        "page.effect.slide.title",
                        slide));

        // Shrink effect
        EffectQueue shrink = new EffectQueue("effectShrink");
        shrink.add(new Shrink());
        shrink.add(new Appear());
        effects.put("effectShrink",
                new EffectWrapper(
                        "page.effect.shrink.title",
                        shrink));
        effects.put("effectAppear",
                new EffectWrapper(
                        "page.effect.shrink.title",
                        new Appear()));
    }

    public EffectWrapper getCurrentEffectWrapper() {
        return currentEffectWrapper;
    }

    public void setCurrentEffecWrapper(EffectWrapper currentEffectWrapper) {
        this.currentEffectWrapper = currentEffectWrapper;
    }

    public HashMap getEffects() {
        return effects;
    }

    /**
     * Gets a list of EffectWrapper objects.
     * @return collection of EffectWrapper
     */
    public Collection getEffectKeys() {
        return effects.keySet();
    }

    /**
     * Wrapper class to make it easy to display the different Effects with a
     * discriptive label. 
     */
    public class EffectWrapper {
        private Effect effect;
        private String title;

        public EffectWrapper(String title, Effect effect) {
            this.effect = effect;
            effect.setTransitory(false);
            this.effect.setFired(true);
            this.title = title;
        }

        public Effect getEffect() {
            return effect;
        }

        public void setEffect(Effect effect) {
            this.effect = effect;
        }

        
    }
    
    public void changeEffectAction() {

      
       
        // do a look up for the effect
        EffectsModel.EffectWrapper effectWrapper = (EffectsModel.EffectWrapper)
                this.getEffects().get("effectBlindDown");

        // if found we reset the effect to fire on the soon to occure
        // response. 
        if (effectWrapper != null) {
        	
        	effectWrapper.getEffect().setSubmit(true);
        	effectWrapper.getEffect().setTransitory(false);
            effectWrapper.getEffect().setFired(false);
            this.setCurrentEffecWrapper(effectWrapper);
        }

    }

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}
}