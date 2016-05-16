package com.hendris;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.platform.win32.WinUser;

public class Teste {
   public interface User32 extends StdCallLibrary {
      User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
      boolean EnumWindows(WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);
      boolean EnumChildWindows(HWND hWnd,WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);
      int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);
      boolean IsWindow(HWND hWnd);
      boolean IsWindowVisible(HWND hWnd);

      HWND FindWindowEx(HWND hwndParent, HWND hwndChildAfter, byte[] lpszClass, byte[] lpszWindow);
   }

   public static void main(String[] args) {
      System.out.println("init main");
      final User32 user32 = User32.INSTANCE;

      final WinUser.WNDENUMPROC wndenumproc = new WinUser.WNDENUMPROC() {
         public boolean callback(HWND hwnd, Pointer pointer) {
            byte[] windowText = new byte[512];
            user32.GetWindowTextA(hwnd, windowText, 512);
            String wText = Native.toString(windowText);

            if (wText.isEmpty()) {
               return true;
            }

            System.out.println("childWindow: " + hwnd + ", Text: " + wText);

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
            if (isWindowVisible) {
               System.out.println("Found window with text " + hWnd + ", total " + ++count
                       + " Text: " + wText + " IsWindow: " + isWindow);

               HWND hwndChild = user32.FindWindowEx(hWnd, null, "Button".getBytes(), "1".getBytes());
               System.out.println("child: " + hwndChild);

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