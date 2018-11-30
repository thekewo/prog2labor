/*
 * LabirintusServlet.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak egy
 * HTTP szervletes �letre kelt�s�re ad p�ld�t ez az oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVil�g
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJ�t�k
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.H�l�zatiLabirintus
 * @see javattanitok.T�voliLabirintus
 * @see javattanitok.Korb�sLabirintus
 */
public class LabirintusServlet extends javax.servlet.http.HttpServlet {
    /**
     * A HTTP GET k�r�s kiszolg�l�sa.
     *
     * @param httpK�r�s a HTTP k�r�st reprezent�l� objektum
     * @param httpV�lasz a HTTP v�laszt reprezent�l� objektum
     */
    protected void doGet(javax.servlet.http.HttpServletRequest httpK�r�s,
            javax.servlet.http.HttpServletResponse httpV�lasz)
            throws javax.servlet.ServletException, java.io.IOException {
        // A v�lasz csatorn�n k�ld�tt adatokat a b�ng�sz�
        // mint html oldalt �rtelmezze 
        httpV�lasz.setContentType("text/html;charset=UTF-8");
        // Elk�rj�k a b�ng�sz�be men� csatorn�t
        java.io.PrintWriter csatornaB�ng�sz�be = httpV�lasz.getWriter();
        // Elkezdj�k bele�rni v�laszunkat html-ben
        csatornaB�ng�sz�be.println("<html>");
        csatornaB�ng�sz�be.println("<head>");
        // A b�ng�sz�ablek c�me
        csatornaB�ng�sz�be.println("<title>Javat tan�tok LabirintusServlet</title>");
        csatornaB�ng�sz�be.println("</head>");
        csatornaB�ng�sz�be.println("<body>");
        // Ez a b�ng�sz� kapcsolatban van m�r a szerver�nkkel?
        javax.servlet.http.HttpSession session = httpK�r�s.getSession();
        // A h�st �s a labirintust majd ebben a kapcsolatot
        // reprezent�l� objektumban t�roljuk.
        Labirintus labirintus = null;
        H�s h�s = null;
        // Ha a kapcsolat most �p�lt fel        
        if(session.isNew()) {
            // akkor elk�sz�t�nk egy �j labirintust �s h�st
            csatornaB�ng�sz�be.println("Hell�, �j j�t�kot kezd�nk!<br>");
            labirintus = new Labirintus(6, 3);
            h�s = new H�s(labirintus);
            h�s.sor(9);
            h�s.oszlop(0);
            // majd betessz�k a kapcsolatot reprezent�l� objektumba, hogy a
            // legk�zelebbi k�r�s�vel j�v� ugyanazon j�t�kos ki tudja venni
            session.setAttribute("labirintus", labirintus);
            session.setAttribute("hos", h�s);
        
        // K�l�nben, azaz, ha m�r volt kapcslat
        } else {
            // akkor kivessz�k a kapcsolatot reprezent�l� objektumb�l
            csatornaB�ng�sz�be.println("Hell�, r�gi j�t�kot folytatjuk<br>");
            labirintus = (Labirintus)session.getAttribute("labirintus");
            h�s = (H�s)session.getAttribute("hos");
            
            if(h�s == null || labirintus == null) {
                // Ha esetleg a tesztel�s sor�n gond lenne a session kezel�ssel...
                log("�j labirintust k�sz�tett�nk...");
                labirintus = new Labirintus(6, 3);
                h�s = new H�s(labirintus);
                h�s.sor(9);
                h�s.oszlop(0);
                session.setAttribute("labirintus", labirintus);
                session.setAttribute("hos", h�s);
                
            }
            
        }        
        // A v�lasz lapra ki�rjuk a h�s l�p�s�n�l a v�laszt�si lehet�s�geket
        // a ?lepes= megfelel� ir�ny form�ban
        csatornaB�ng�sz�be.println("<br>Merre l�pjen a h�s?<br>");
        csatornaB�ng�sz�be.println("<a href=\"/JavatTanitokWebes/" +
                "labirintus?lepes=fol\">F�l</a>");
        csatornaB�ng�sz�be.println("<br>");
        csatornaB�ng�sz�be.println("<a href=\"/JavatTanitokWebes/" +
                "labirintus?lepes=le\">Le</a>");
        csatornaB�ng�sz�be.println("<br>");
        csatornaB�ng�sz�be.println("<a href=\"/JavatTanitokWebes/" +
                "labirintus?lepes=jobbra\">Jobbra</a>");
        csatornaB�ng�sz�be.println("<br>");
        csatornaB�ng�sz�be.println("<a href=\"/JavatTanitokWebes/" +
                "labirintus?lepes=balra\">Balra</a>");        

        // A mostani k�r�sben j�tt valami inf� a l�p�ssel kapcsolatban?
        String lepesString = httpK�r�s.getParameter("lepes");
        // Ha igen, akkor annak megfelel�en l�p�nk
        if("fol".equals(lepesString))
            h�s.l�pF�l();
        else if("le".equals(lepesString))
            h�s.l�pLe();
        else if("jobbra".equals(lepesString))
            h�s.l�pJobbra();
        else if("balra".equals(lepesString))
            h�s.l�pBalra();
        
        // T�rt�nt ezzel a l�p�ssel valami �rdekes a labirintusban?
            switch(labirintus.bolyong(h�s)) {
                
                case Labirintus.J�T�K_MEGY_H�S_RENDBEN:
                    break;
                case Labirintus.J�T�K_MEGY_MEGHALT_H�S:
                    // M�g van �lete, visszatessz�k a kezd� poz�ci�ra
                    h�s.sor(9);
                    h�s.oszlop(0);
                    break;                    
                case Labirintus.J�T�K_V�GE_MINDEN_KINCS_MEGVAN:
                    csatornaB�ng�sz�be.println("<h1>GY�ZT�L</h1>");
                    session.invalidate();                    
                    break;                    
                case Labirintus.J�T�K_V�GE_MEGHALT_H�S:
                    session.invalidate();
                    csatornaB�ng�sz�be.println("<h1>VESZTETT�L</h1>");
                    break;                    
            }
            // Kinyomtatjuk a labirintus aktu�lis �llapot�t
            csatornaB�ng�sz�be.println("<pre>");
            csatornaB�ng�sz�be.println(labirintus.kinyomtat(h�s));
            csatornaB�ng�sz�be.println("</pre>");
            // �s n�h�ny inf�t a j�t�k aktu�lis adataib�l
            csatornaB�ng�sz�be.println("<br>");
            csatornaB�ng�sz�be.println("<br>�letek sz�ma: " + h�s.�letek());
            csatornaB�ng�sz�be.println("<br>Gy�jt�tt �rt�k: "+h�s.pontsz�m());            
            csatornaB�ng�sz�be.println("</body>");
            csatornaB�ng�sz�be.println("</html>");
            // Z�rjuk a b�ng�sz�be ir�nyul� csatorn�t.
            csatornaB�ng�sz�be.close();
    }    
}
