package io.github.linwancen.plugin.show.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent mySettingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Show Comment Global.";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        AppSettingsState settings = AppSettingsState.getInstance();
        boolean modified = mySettingsComponent.getShowTreeComment() != settings.showTreeComment;
        modified |= mySettingsComponent.getShowLineEndComment() != settings.showLineEndComment;

        modified |= !mySettingsComponent.getLineEndCount().equals(String.valueOf(settings.lineEndCount));
        modified |= !mySettingsComponent.getLineEndColor().equals(settings.lineEndTextAttr.getForegroundColor());
        modified |= !mySettingsComponent.getLineEndJsonColor().equals(settings.lineEndJsonTextAttr.getForegroundColor());
        modified |= !mySettingsComponent.getLineEndPrefix().equals(settings.lineEndPrefix);

        modified |= mySettingsComponent.getFindElementRightToLeft() != settings.findElementRightToLeft;

        modified |= mySettingsComponent.getFromCall() != settings.fromCall;
        modified |= mySettingsComponent.getFromNew() != settings.fromNew;
        modified |= mySettingsComponent.getFromRef() != settings.fromRef;
        modified |= mySettingsComponent.getInJson() != settings.inJson;
        modified |= mySettingsComponent.getSkipAnnotation() != settings.skipAnnotation;
        modified |= mySettingsComponent.getSkipAscii() != settings.skipAscii;
        modified |= mySettingsComponent.getSkipBlank() != settings.skipBlank;

        modified = AbstractSettingsConfigurable.isModified(settings, mySettingsComponent, modified);

        return modified;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.showTreeComment = mySettingsComponent.getShowTreeComment();
        settings.showLineEndComment = mySettingsComponent.getShowLineEndComment();

        try {
            settings.lineEndCount = Integer.parseInt(mySettingsComponent.getLineEndCount());
        } catch (NumberFormatException e) {
            mySettingsComponent.setLineEndCount(String.valueOf(settings.lineEndCount));
        }
        settings.lineEndTextAttr.setForegroundColor(mySettingsComponent.getLineEndColor());
        settings.lineEndJsonTextAttr.setForegroundColor(mySettingsComponent.getLineEndJsonColor());
        settings.lineEndPrefix = mySettingsComponent.getLineEndPrefix();

        settings.findElementRightToLeft = mySettingsComponent.getFindElementRightToLeft();

        settings.fromCall = mySettingsComponent.getFromCall();
        settings.fromNew = mySettingsComponent.getFromNew();
        settings.fromRef = mySettingsComponent.getFromRef();
        settings.inJson = mySettingsComponent.getInJson();
        settings.skipAnnotation = mySettingsComponent.getSkipAnnotation();
        settings.skipAscii = mySettingsComponent.getSkipAscii();
        settings.skipBlank = mySettingsComponent.getSkipBlank();

        AbstractSettingsConfigurable.apply(settings, mySettingsComponent);
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        mySettingsComponent.setShowTreeComment(settings.showTreeComment);
        mySettingsComponent.setShowLineEndComment(settings.showLineEndComment);

        mySettingsComponent.setLineEndCount(String.valueOf(settings.lineEndCount));
        mySettingsComponent.setLineEndColor(settings.lineEndTextAttr.getForegroundColor());
        mySettingsComponent.setLineEndJsonColor(settings.lineEndJsonTextAttr.getForegroundColor());
        mySettingsComponent.setLineEndPrefix(settings.lineEndPrefix);

        mySettingsComponent.setFindElementRightToLeft(settings.findElementRightToLeft);

        mySettingsComponent.setFromCall(settings.fromCall);
        mySettingsComponent.setFromNew(settings.fromNew);
        mySettingsComponent.setFromRef(settings.fromRef);
        mySettingsComponent.setInJson(settings.inJson);
        mySettingsComponent.setSkipAnnotation(settings.skipAnnotation);
        mySettingsComponent.setSkipAscii(settings.skipAscii);
        mySettingsComponent.setSkipBlank(settings.skipBlank);

        AbstractSettingsConfigurable.reset(settings, mySettingsComponent);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}
