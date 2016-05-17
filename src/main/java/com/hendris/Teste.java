package com.hendris;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.W32APIOptions;

public class Teste {
   public interface User32 extends StdCallLibrary {
      //User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
      User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);


      boolean EnumWindows(WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);
      boolean EnumChildWindows(HWND hWnd,WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);
      int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);
      boolean IsWindow(HWND hWnd);
      boolean IsWindowVisible(HWND hWnd);

//      HWND FindWindowEx(HWND hwndParent, HWND hwndChildAfter, byte[] lpszClass, byte[] lpszWindow);
      boolean FindWindowEx(HWND parent, HWND child, String className, String window);

      int GetClassNameA(HWND in, byte[] lpString, int size);
      int GetDlgItemText(HWND hDlg, int nIDDlgItem, byte[] lpString, int nMaxCount);
      int GetDlgCtrlID(HWND hwndCtl);
      HWND SetFocus(HWND hWnd);
      boolean ShowWindow(HWND hWnd, int nCmdShow);
//      boolean BringWindowToTop(HWND hWnd);
      boolean SetForegroundWindow(HWND hWnd); //ok

      int GetWindowRect(HWND handle, int[] rect); //ok
   }

   public static void main(String[] args) {
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
            //if (isWindowVisible && wText.contains("Hold")) {
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



      //user32.
   }
}