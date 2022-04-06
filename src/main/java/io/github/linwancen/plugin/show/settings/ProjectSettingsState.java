package io.github.linwancen.plugin.show.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

@State(
        name = "io.github.linwancen.plugin.comment.settings.ProjectSettingsState",
        storages = @Storage("ShowCommentProject.xml")
)
public class ProjectSettingsState implements PersistentStateComponent<ProjectSettingsState> {

    public boolean globalFilterEffective = true;
    public boolean projectFilterEffective = false;
    public String lineEndInclude = "";
    public String lineEndExclude = "";
    public String[] lineEndIncludeArray = {};
    public String[] lineEndExcludeArray = {};
    public Pattern extReplaceToSpace = Pattern.compile("");
    public int extDocColumn = 2;

    public static ProjectSettingsState getInstance(Project project) {
        return project.getService(ProjectSettingsState.class);
    }

    @Nullable
    @Override
    public ProjectSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ProjectSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
