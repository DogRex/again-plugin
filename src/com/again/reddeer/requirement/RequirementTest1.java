package com.again.reddeer.requirement;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(PluginPerspective.class)
public class RequirementTest1{
	
	@Test
	public void testOpenNewPluginWizard()
	{
		new ShellMenu("File","New","Plug-in Project").select();
	}
}
