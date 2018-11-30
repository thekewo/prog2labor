/*
 * LabirintusServlet.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának egy
 * HTTP szervletes életre keltésére ad példát ez az osztály.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVilág
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJáték
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.HálózatiLabirintus
 * @see javattanitok.TávoliLabirintus
 * @see javattanitok.KorbásLabirintus
 */
public class LabirintusServlet extends javax.servlet.http.HttpServlet {
    /**
     * A HTTP GET kérés kiszolgálása.
     *
     * @param httpKérés a HTTP kérést reprezentáló objektum
     * @param httpVálasz a HTTP választ reprezentáló objektum
     */
    protected void doGet(javax.servlet.http.HttpServletRequest httpKérés,
            javax.servlet.http.HttpServletResponse httpVálasz)
            throws javax.servlet.ServletException, java.io.IOException {
        // A válasz csatornán küldött adatokat a böngészõ
        // mint html oldalt értelmezze 
        httpVálasz.setContentType("text/html;charset=UTF-8");
        // Elkérjük a böngészõbe menõ csatornát
        java.io.PrintWriter csatornaBöngészõbe = httpVálasz.getWriter();
        // Elkezdjük beleírni válaszunkat html-ben
        csatornaBöngészõbe.println("<html>");
        csatornaBöngészõbe.println("<head>");
        // A böngészõablek címe
        csatornaBöngészõbe.println("<title>Javat tanítok LabirintusServlet</title>");
        csatornaBöngészõbe.println("</head>");
        csatornaBöngészõbe.println("<body>");
        // Ez a böngészõ kapcsolatban van már a szerverünkkel?
        javax.servlet.http.HttpSession session = httpKérés.getSession();
        // A hõst és a labirintust majd ebben a kapcsolatot
        // reprezentáló objektumban tároljuk.
        Labirintus labirintus = null;
        Hõs hõs = null;
        // Ha a kapcsolat most épült fel        
        if(session.isNew()) {
            // akkor elkészítünk egy új labirintust és hõst
            csatornaBöngészõbe.println("Helló, új játékot kezdünk!<br>");
            labirintus = new Labirintus(6, 3);
            hõs = new Hõs(labirintus);
            hõs.sor(9);
            hõs.oszlop(0);
            // majd betesszük a kapcsolatot reprezentáló objektumba, hogy a
            // legközelebbi kérésével jövõ ugyanazon játékos ki tudja venni
            session.setAttribute("labirintus", labirintus);
            session.setAttribute("hos", hõs);
        
        // Különben, azaz, ha már volt kapcslat
        } else {
            // akkor kivesszük a kapcsolatot reprezentáló objektumból
            csatornaBöngészõbe.println("Helló, régi játékot folytatjuk<br>");
            labirintus = (Labirintus)session.getAttribute("labirintus");
            hõs = (Hõs)session.getAttribute("hos");
            
            if(hõs == null || labirintus == null) {
                // Ha esetleg a tesztelés során gond lenne a session kezeléssel...
                log("Új labirintust készítettünk...");
                labirintus = new Labirintus(6, 3);
                hõs = new Hõs(labirintus);
                hõs.sor(9);
                hõs.oszlop(0);
                session.setAttribute("labirintus", labirintus);
                session.setAttribute("hos", hõs);
                
            }
            
        }        
        // A válasz lapra kiírjuk a hõs lépésénél a választási lehetõségeket
        // a ?lepes= megfelelõ irány formában
        csatornaBöngészõbe.println("<br>Merre lépjen a hõs?<br>");
        csatornaBöngészõbe.println("<a href=\"/JavatTanitokWebes/" +
                "labirintus?lepes=fol\">Föl</a>");
        csatornaBöngészõbe.println("<br>");
        csatornaBöngészõbe.println("<a href=\"/JavatTanitokWebes/" +
                "labirintus?lepes=le\">Le</a>");
        csatornaBöngészõbe.println("<br>");
        csatornaBöngészõbe.println("<a href=\"/JavatTanitokWebes/" +
                "labirintus?lepes=jobbra\">Jobbra</a>");
        csatornaBöngészõbe.println("<br>");
        csatornaBöngészõbe.println("<a href=\"/JavatTanitokWebes/" +
                "labirintus?lepes=balra\">Balra</a>");        

        // A mostani kérésben jött valami infó a lépéssel kapcsolatban?
        String lepesString = httpKérés.getParameter("lepes");
        // Ha igen, akkor annak megfelelõen lépünk
        if("fol".equals(lepesString))
            hõs.lépFöl();
        else if("le".equals(lepesString))
            hõs.lépLe();
        else if("jobbra".equals(lepesString))
            hõs.lépJobbra();
        else if("balra".equals(lepesString))
            hõs.lépBalra();
        
        // Történt ezzel a lépéssel valami érdekes a labirintusban?
            switch(labirintus.bolyong(hõs)) {
                
                case Labirintus.JÁTÉK_MEGY_HÕS_RENDBEN:
                    break;
                case Labirintus.JÁTÉK_MEGY_MEGHALT_HÕS:
                    // Még van élete, visszatesszük a kezdõ pozícióra
                    hõs.sor(9);
                    hõs.oszlop(0);
                    break;                    
                case Labirintus.JÁTÉK_VÉGE_MINDEN_KINCS_MEGVAN:
                    csatornaBöngészõbe.println("<h1>GYÕZTÉL</h1>");
                    session.invalidate();                    
                    break;                    
                case Labirintus.JÁTÉK_VÉGE_MEGHALT_HÕS:
                    session.invalidate();
                    csatornaBöngészõbe.println("<h1>VESZTETTÉL</h1>");
                    break;                    
            }
            // Kinyomtatjuk a labirintus aktuális állapotát
            csatornaBöngészõbe.println("<pre>");
            csatornaBöngészõbe.println(labirintus.kinyomtat(hõs));
            csatornaBöngészõbe.println("</pre>");
            // és néhány infót a játék aktuális adataiból
            csatornaBöngészõbe.println("<br>");
            csatornaBöngészõbe.println("<br>Életek száma: " + hõs.életek());
            csatornaBöngészõbe.println("<br>Gyûjtött érték: "+hõs.pontszám());            
            csatornaBöngészõbe.println("</body>");
            csatornaBöngészõbe.println("</html>");
            // Zárjuk a böngészõbe irányuló csatornát.
            csatornaBöngészõbe.close();
    }    
}
