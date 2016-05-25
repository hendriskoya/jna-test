package com.hendris.winapi;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import java.util.ArrayList;
import java.util.List;

public class WinAPI {

   private final String username = "jimmyhendris";

   public static void main(String[] args) {
      WinAPI winAPI = new WinAPI();
      List<Window> windows = winAPI.loadWindows();
      for (Window window: windows) {
         System.out.println(window);
      }
   }

   private final User32 user32 = User32.INSTANCE;

   public List<Window> loadWindows() {
      List<Window> windows = new ArrayList<>();
      user32.EnumWindows(new WinUser.WNDENUMPROC() {
         int count = 0;

         public boolean callback(HWND hWnd, Pointer lParam) {
            byte[] windowText = new byte[512];
            user32.GetWindowTextA(hWnd, windowText, 512);
            String wText = Native.toString(windowText);

            // get rid of this if block if you want all windows regardless of whether
            // or not they have text
            if (wText.isEmpty()) {
               return true;
            }
            boolean isWindowVisible = user32.IsWindowVisible(hWnd);
            if (isWindowVisible && wText.contains(username)) {
               System.out.println("Found window with text " + hWnd + ", total " + ++count
                       + " Text: " + wText);

               int[] rect = {0, 0, 0, 0};
               int b = user32.GetWindowRect(hWnd, rect);
               if (b == 0) {
                  System.out.println("Ocorreu erro ao obter o GetWindowRect");
               } else {
                  System.out.println(String.format("%d %d %d %d", rect[0], rect[1], rect[2], rect[3]));
               }

               windows.add(new Window(wText, hWnd, rect));
            }
            return true;
         }
      }, null);
      return windows;
   }



   public void teste() {
      System.out.println("init main");
      final User32 user32 = User32.INSTANCE;

      final WinUser.WNDENUMPROC wndenumproc = new WinUser.WNDENUMPROC() {
         public boolean callback(HWND hwnd, Pointer pointer) {
            byte[] windowText = new byte[512];
            user32.GetClassNameA(hwnd, windowText, 512);
            String className = Native.toString(windowText);
//            System.out.println("child: " + Native.toString(windowText));
            if (className.equals("PokerStarsButtonClass")) {
               System.out.println("child: " + Native.toString(windowText));

               int id = user32.GetDlgCtrlID(hwnd);
               System.out.println("id: " + id);

               byte[] text = new byte[512];
               int n = user32.GetDlgItemText(hwnd, 1000, text, 512);
               System.out.println("n: " + n + ", " + Native.toString(text));
            }
            /*user32.GetWindowTextA(hwnd, windowText, 512);
            String wText = Native.toString(windowText);

            if (wText.isEmpty()) {
               return true;
            }

            System.out.println("childWindow: " + hwnd + ", Text: " + wText);*/

            return true;
         }
      };


      user32.EnumWindows(new WinUser.WNDENUMPROC() {
         int count = 0;

         public boolean callback(HWND hWnd, Pointer arg1) {
            byte[] windowText = new byte[512];
            user32.GetWindowTextA(hWnd, windowText, 512);
            String wText = Native.toString(windowText);

            // get rid of this if block if you want all windows regardless of whether
            // or not they have text
            if (wText.isEmpty()) {
               return true;
            }
            boolean isWindow = user32.IsWindow(hWnd);
            boolean isWindowVisible = user32.IsWindowVisible(hWnd);
//            if (isWindowVisible && wText.contains(username)) {
            if (isWindowVisible && wText.toUpperCase().contains("NOTEPAD")) {
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

               /*System.out.println("");
               user32.EnumChildWindows(hWnd, wndenumproc, null);
               System.out.println("");*/
            }
            return true;
         }
      }, null);
   }
}