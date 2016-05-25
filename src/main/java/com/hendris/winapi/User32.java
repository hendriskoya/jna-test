package com.hendris.winapi;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface User32 extends StdCallLibrary, WinUser {
      //User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
      User32 INSTANCE = (User32)Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
      HWND HWND_MESSAGE = new HWND(Pointer.createConstant(-3));

      boolean EnumWindows(WNDENUMPROC lpEnumFunc, Pointer arg);
      boolean EnumChildWindows(HWND hWnd, WNDENUMPROC lpEnumFunc, Pointer arg);
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