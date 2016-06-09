package com.hendris.winapi;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;

import java.util.*;

public class WinAPI {

   private static String username = "Notepad";

   public static void main(String[] args) {
      /*WinAPI winAPI = new WinAPI();
      List<Window> windows = winAPI.loadWindows();
      for (Window window: windows) {
         System.out.println(window);
      }*/
      if (args.length > 0) {
         username = args[0];
      }
      WinAPI instance = WinAPI.INSTANCE;
   }

   public static final WinAPI INSTANCE = new WinAPI();

   private final User32 user32 = User32.INSTANCE;

   private Map<HWND, Window> windowsMap = new HashMap<>();

   public WinAPI() {
      prepareWindows();
   }

   private void prepareWindows() {
      //TODO Incluir tratamento para remover do map as janelas não existentes.
      //TODO Incluir tratamento para atualizar map com novos windows.
      List<Window> windows = loadWindows();
      for (Window window: windows) {
         windowsMap.put(window.getHandle(), window);
         //System.out.println(showWindow(window));
      }
   }

   public Collection<Window> getLoadedWindows() {
      return windowsMap.values();
   }

   public boolean showWindow(Window window) {
      return user32.SetForegroundWindow(window.getHandle());
   }

   private List<Window> loadWindows() {
      List<Window> windows = new ArrayList<>();
      user32.EnumWindows(new WinUser.WNDENUMPROC() {
         int count = 0;

         public boolean callback(HWND hWnd, Pointer lParam) {
            String wText = getCaption(hWnd);

            // get rid of this if block if you want all windows regardless of whether
            // or not they have text
            if (wText.isEmpty()) {
               return true;
            }
            boolean isWindowVisible = user32.IsWindowVisible(hWnd);
            if (isWindowVisible && wText.contains(username)) {
               System.out.println("Found window with text " + hWnd + ", total " + ++count
                       + " Text: " + wText);

//               System.out.println(user32.SetForegroundWindow(hWnd));

               int[] rect = {0, 0, 0, 0};
               int b = user32.GetWindowRect(hWnd, rect);
               if (b == 0) {
                  System.out.println("Ocorreu erro ao obter o GetWindowRect");
               } else {
                  System.out.println(String.format("%d %d %d %d", rect[0], rect[1], rect[2], rect[3]));
               }

               windows.add(new Window(wText, hWnd, rect, null));
            }
            return true;
         }
      }, null);
      return windows;
   }



   public void teste(String windowCaption) {
      System.out.println("init main");
      final User32 user32 = User32.INSTANCE;



      HWND mainHWnd;

      user32.EnumWindows(new WinUser.WNDENUMPROC() {
         int count = 0;

         public boolean callback(HWND hWnd, Pointer arg1) {

            String wText = getCaption(hWnd);

            // get rid of this if block if you want all windows regardless of whether
            // or not they have text
            if (wText.isEmpty()) {
               return true;
            }
            boolean isWindow = user32.IsWindow(hWnd);
            boolean isWindowVisible = user32.IsWindowVisible(hWnd);
//            if (isWindowVisible && wText.contains(username)) {
            if (isWindowVisible && wText.toUpperCase().contains(windowCaption.toUpperCase())) {
               System.out.println("Found window with text " + hWnd + ", total " + ++count
                       + " Text: " + wText + " IsWindow: " + isWindow);

               //System.out.println(user32.ShowWindow(hWnd, 5));
               //System.out.println(user32.SetForegroundWindow(hWnd));

               int[] rect = {0, 0, 0, 0};
               int b = user32.GetWindowRect(hWnd, rect);
               if (b == 0) {
                  System.out.println("Ocorreu erro ao obter o GetWindowRect");
               } else {
                  System.out.println(String.format("%d %d %d %d", rect[0], rect[1], rect[2], rect[3]));
               }

               enumChildWindows(hWnd, user32);
            }
            return true;
         }
      }, null);
   }

   private void enumChildWindows(final HWND mainHWnd, User32 user32) {

      final HWND[] teste = new HWND[1];

      final WinUser.WNDENUMPROC wndenumproc = new WinUser.WNDENUMPROC() {
         public boolean callback(HWND hwnd, Pointer pointer) {
            byte[] windowText = new byte[512];
            user32.GetClassNameA(hwnd, windowText, 512);
            String className = Native.toString(windowText);
//            System.out.println("child: " + Native.toString(windowText));

            if (className.equals("CalcFrame")) {
               System.out.println("CalcFrame set");
               teste[0] = hwnd;
            }

            //if (className.equals("PokerStarsButtonClass")) {
            System.out.println("child: " + Native.toString(windowText));

            String wText = getCaption(hwnd);
            System.out.println("wText: " + wText);

            int id = user32.GetDlgCtrlID(hwnd);
            System.out.println("id: " + id);

            HWND hwndItem = user32.GetDlgItem(mainHWnd, id);
            System.out.println("handle: " + hwndItem);

            byte[] text = new byte[512];
            int n = user32.GetDlgItemTextA(teste[0], id, text, 512);
            System.out.println("n: " + n + ", " + Native.toString(text));
            //}

            /*user32.GetWindowTextA(hwnd, windowText, 512);
            String wText = Native.toString(windowText);

            if (wText.isEmpty()) {
               return true;
            }

            System.out.println("childWindow: " + hwnd + ", Text: " + wText);*/

            return true;
         }
      };

      System.out.println("\nstart EnumChildWindows");
      user32.EnumChildWindows(mainHWnd, wndenumproc, null);
      System.out.println("");
   }

   private String getCaption(HWND hWnd) {
      byte[] windowText = new byte[512];
      user32.GetWindowTextA(hWnd, windowText, 512);
      return Native.toString(windowText);
   }
}