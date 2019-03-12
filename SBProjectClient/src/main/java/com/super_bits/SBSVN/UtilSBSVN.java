/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.SBSVN;

import com.super_bits.sbprojectclient.TrabalharNoProjeto;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * 
 * 
 * @author Salvio
 */
public class UtilSBSVN {

    public static SVNCommitInfo commitaTudo(final String repositoryURL, final String subVersionedDirectory, final String userName, final String hashedPassword, final String commitMessage) throws SVNException {
      throw new UnsupportedOperationException();
    //    final SVNClientManager cm = SVNClientManager.newInstance(new DefaultSVNOptions(), userName, hashedPassword);
      //  cm.getCommitClient().doImport(new File(subVersionedDirectory), SVNURL.parseURIEncoded(userName).parseURIEncoded(repositoryURL), "<import> " + commitMessage, null, false, true, SVNDepth.fromRecurse(true));
      //  cm.getCommitClient().doCommit(paths, true, commitMessage, null, changelists, true, true, SVNDepth.EMPTY)
               
    //    return cm.getCommitClient(). doCommit(new File[]{new File(subVersionedDirectory)}, false, "<commit> " + commitMessage, null, null, false, true, SVNDepth.INFINITY);
    }
    
    
    public static void tortoiseCommit(String pDiretorio,String pComentario){
           Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("TortoiseProc.exe /command:commit /path:\""+pDiretorio+"\" /notempfile /logmsg:\""+pComentario+"\" /closeonend:3");
            //     UtilSBSVN.commitaTudo("http://sbbh.ddns.info:8000/usvn/svn/SBProject/trunk","/home/projetosSB/projetos/SBProject","SBAdmin"  ,"123321@aA","Commit teste");
        } catch (IOException ex) {
            Logger.getLogger(TrabalharNoProjeto.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
}
