package org.consulo.google.protobuf.vfs;

import org.consulo.google.protobuf.module.extension.GoogleProtobufModuleExtensionUtil;
import org.consulo.google.protobuf.module.extension.GoogleProtobufSupportProvider;
import org.consulo.vfs.backgroundTask.BackgroundTaskByVfsChangeProvider;
import org.consulo.vfs.backgroundTask.BackgroundTaskByVfsParameters;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author VISTALL
 * @since 07.10.13.
 */
public class PbBackgroundTaskByVfsChangeProvider extends BackgroundTaskByVfsChangeProvider
{
	@NotNull
	@Override
	public String getName()
	{
		return "Google Protobuf";
	}

	@Override
	public boolean validate(@NotNull Project project, @NotNull VirtualFile virtualFile)
	{
		return GoogleProtobufModuleExtensionUtil.getProvider(project, virtualFile) != null;
	}

	@Override
	public void setDefaultParameters(@NotNull Project project, @NotNull VirtualFile virtualFile, @NotNull BackgroundTaskByVfsParameters backgroundTaskByVfsParameters)
	{
		GoogleProtobufSupportProvider provider = GoogleProtobufModuleExtensionUtil.getProvider(project, virtualFile);

		assert provider != null;

		backgroundTaskByVfsParameters.setExePath(provider.getExePath());

		backgroundTaskByVfsParameters.setProgramParameters(StringUtil.join(provider.getAdditionalArguments(), " "));
		backgroundTaskByVfsParameters.setWorkingDirectory("$FileParentPath$");
		backgroundTaskByVfsParameters.setOutPath("$FileParentPath$");
	}
}
