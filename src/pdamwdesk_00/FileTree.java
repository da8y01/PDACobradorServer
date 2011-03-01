package pdamwdesk_00;
/*
 * Copyright (c) Ian F. Darwin, http://www.darwinsys.com/, 1996-2002.
 * All rights reserved. Software written by Ian F. Darwin and others.
 * $Id: LICENSE,v 1.8 2004/02/09 03:33:38 ian Exp $
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Java, the Duke mascot, and all variants of Sun's Java "steaming coffee
 * cup" logo are trademarks of Sun Microsystems. Sun's, and James Gosling's,
 * pioneering role in inventing and promulgating (and standardizing) the Java
 * language and environment is gratefully acknowledged.
 *
 * The pioneering role of Dennis Ritchie and Bjarne Stroustrup, of AT&T, for
 * inventing predecessor languages C and C++ is also gratefully acknowledged.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Display a file system in a JTree view
 *
 * @version $Id: FileTree.java,v 1.9 2004/02/23 03:39:22 ian Exp $
 * @author Ian Darwin
 */
public class FileTree extends JPanel {
  /** Construct a FileTree */
  public FileTree(File dir) {
    setLayout(new BorderLayout());

    // Make a tree list with all the nodes, and make it a JTree
    JTree tree = new JTree(addNodes(null, dir));

    // Add a listener
    tree.addTreeSelectionListener(new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
            .getPath().getLastPathComponent();
        System.out.println("You selected " + node);
      }
    });

    // Lastly, put the JTree into a JScrollPane.
    JScrollPane scrollpane = new JScrollPane();
    scrollpane.getViewport().add(tree);
    add(BorderLayout.CENTER, scrollpane);
  }

  /** Add nodes from under "dir" into curTop. Highly recursive. */
  DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
    String curPath = dir.getPath(); // setea la cadena current path
    DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);    // setea el objeto current dir como un objeto DefaultMutableTreeNode en la cadena current path
    if (curTop != null) { // should only be null at root    // si el parámetro objeto current top es diferente de nulo
      curTop.add(curDir);   // entonces añadir al objeto current top el objeto current dir
    }
    Vector ol = new Vector();   // inicializar un vector ol object list
    String[] tmp = dir.list();  // inicializar un arreglo temporal con los contenidos del parametro archivo dir
    for (int i = 0; i < tmp.length; i++)    // iniciar un ciclo desde 0 hasta la longitud del arreglo temporal, es decir la cantidad de objetos del FS contenidos en el objeto archivo dir
      ol.addElement(tmp[i]);    // añadir el elemento actual al vector ol object list
    Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);    // ordenar el vector ol con la regla ordenamiento alfabetico insensitivo
    File f; // declarar el objeto archivo f
    Vector files = new Vector();    // inicializar un objecto vector files
    // Make two passes, one for Dirs and one for Files. This is #1.
    for (int i = 0; i < ol.size(); i++) {   // iniciar un ciclo desde 0 hasta el tamaño del vector ol
      String thisObject = (String) ol.elementAt(i); // inicializar la cadena thisObject como el elemento actual del vector ol
      String newPath;   // declarar cadena nueva ruta
      if (curPath.equals("."))  // si la cadena current path es igual "." (directorio actual)
        newPath = thisObject;   // entonces la cadena nueva ruta será igual a la cadena thisObject (será igual al elemento del vector ol actual)
      else  // si no
        newPath = curPath + File.separator + thisObject;    // entonces la cadena nueva ruta será igual a la concatenación de la cadena current path + separador de archivo + thisObject (el objeto actual del vector ol)
      if ((f = new File(newPath)).isDirectory())    // inicializa el objeto archivo f como la nueva ruta, si es un directorio
        addNodes(curDir, f);    // invocar a añadir nodos con los parámetros el objeto DefaultMutableTreeNode current dir y el objeto archivo directorio f
      else  // si no es un directorio
        files.addElement(thisObject);   // añadir al vector files la cadena que representa thisObject
    }
    // Pass two: for files.
    for (int fnum = 0; fnum < files.size(); fnum++)
      curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
    return curDir;
  }

  public Dimension getMinimumSize() {
    return new Dimension(200, 400);
  }

  public Dimension getPreferredSize() {
    return new Dimension(200, 400);
  }

  /** Main: make a Frame, add a FileTree */
  public static void main(String[] av) {

    JFrame frame = new JFrame("FileTree");
    frame.setForeground(Color.black);
    frame.setBackground(Color.lightGray);
    Container cp = frame.getContentPane();

    if (av.length == 0) {
      cp.add(new FileTree(new File(".")));
    } else {
      cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
      for (int i = 0; i < av.length; i++)
        cp.add(new FileTree(new File(av[i])));
    }

    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
