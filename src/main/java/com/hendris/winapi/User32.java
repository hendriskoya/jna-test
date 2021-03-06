package com.hendris.winapi;

import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.PointerByReference;
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
      int GetDlgItemTextA(HWND hDlg, int nIDDlgItem, byte[] lpString, int nMaxCount);
      int GetDlgCtrlID(HWND hwndCtl);
      HWND GetDlgItem(HWND hDlg, int nIDDlgItem);

      HWND SetFocus(HWND hWnd);
      boolean ShowWindow(HWND hWnd, int nCmdShow);
//      boolean BringWindowToTop(HWND hWnd);
      boolean SetForegroundWindow(HWND hWnd); //ok

      int GetWindowRect(HWND handle, int[] rect); //ok

      HWND GetForegroundWindow();
      int GetWindowThreadProcessId(HWND hWnd, PointerByReference lpdwProcessId);
      boolean BringWindowToTop(HWND hWnd);
      boolean AttachThreadInput(DWORD idAttach, DWORD idAttachTo, boolean fAttach);
      //boolean AttachThreadInput(int idAttach, int idAttachTo, boolean fAttach);

      boolean IsIconic(HWND hWnd);
      boolean SystemParametersInfo(int uiAction, int uiParam, PointerByReference pvParam, int fWinIni);

      boolean SetWindowPos(HWND hWnd, HWND hWndInsertAfter, int X, int Y, int cx, int cy, int uFlags);

      HWND SetActiveWindow(HWND hWnd);

      HWND FindWindow(String var1, String var2);

      int SPI_GETFOREGROUNDLOCKTIMEOUT = 0x2000;
      int SPI_SETFOREGROUNDLOCKTIMEOUT = 0x2001;

      int SPIF_SENDCHANGE = 2;
      int SPIF_UPDATEINIFILE = 0x01;

      int SWP_SHOWWINDOW = 0x0040;
      int SWP_NOSIZE = 0x0001;
      int SWP_NOMOVE = 0x0002;

      WinNT.HANDLE SetWinEventHook(int eventMin, int eventMax, HMODULE hmodWinEventProc, WinEventProc winEventProc,
                                   int processID, int threadID, int flags);

      boolean UnhookWinEvent(WinNT.HANDLE handle);

      boolean PeekMessage(MSG var1, HWND var2, int var3, int var4, int var5);

    public static interface WinEventProc extends Callback {
        void callback(WinNT.HANDLE hWinEventHook, DWORD event, HWND hwnd, LONG idObject, LONG idChild,
                DWORD dwEventThread,
                DWORD dwmsEventTime);
    }

      boolean AllowSetForegroundWindow(int dwProcessId);
}