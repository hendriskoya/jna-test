package com.hendris;

import com.hendris.winapi.User32;
import com.hendris.winapi.WinAPI;
import com.hendris.winapi.Window;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.PointerByReference;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by hendris on 5/30/16.
 */
public class Teste {

    private static User32 user32 = User32.INSTANCE;
    private static Kernel32 kernel = Kernel32.INSTANCE;

    public static void main(String[] args) {

        /*WinAPI winAPI = WinAPI.INSTANCE;
        Collection<Window> loadedWindows = winAPI.getLoadedWindows();
        Iterator<Window> iterator = loadedWindows.iterator();
        Window notepad1 = iterator.next();
        Window notepad2 = iterator.next();

        HWND hwnd = notepad1.getHandle();
        HWND hwnd2 = notepad2.getHandle();

        esperar();

        System.out.println(mostrarWindow(hwnd));*/
        //System.out.println(user32.SetForegroundWindow(hwnd));

        /*esperar();

        System.out.println(user32.SetForegroundWindow(hwnd2));

        esperar();

        System.out.println(user32.SetForegroundWindow(hwnd));*/

        esperar();
        mostrarWindow2();
    }

    private static boolean mostrarWindow2() {

        HWND hWnd = user32.FindWindow(null, "New Text Document - Notepad");
        if (hWnd == null) {
            System.out.println("hWnd is null");
            return false;
        }
        PointerByReference pointer = new PointerByReference();
        int foregroundThreadID = user32.GetWindowThreadProcessId(user32.GetForegroundWindow(), pointer);
        int currentThreadId = kernel.GetCurrentThreadId();

        if (foregroundThreadID != currentThreadId) {
            System.out.println("foregroundThreadID != currentThreadId");
            System.out.println(user32.AttachThreadInput(new DWORD(foregroundThreadID), new DWORD(currentThreadId), true));
            System.out.println(user32.BringWindowToTop(hWnd));
            System.out.println(user32.ShowWindow(hWnd,5));
            System.out.println(user32.SetForegroundWindow(hWnd));
            System.out.println(user32.AttachThreadInput(new DWORD(foregroundThreadID), new DWORD(currentThreadId), false));
        } else {
            System.out.println("foregroundThreadID == currentThreadId");
            user32.BringWindowToTop(hWnd);
            user32.ShowWindow(hWnd,5);
        }

        return false;
    }

    private static boolean mostrarWindow(HWND hwnd) {
        if (user32.IsIconic(hwnd)) {
            System.out.println("user32.IsIconic(hwnd)");
            user32.ShowWindow(hwnd, WinUser.SW_RESTORE);
        }

        if (user32.GetForegroundWindow() == hwnd) {
            System.out.println("user32.GetForegroundWindow() == hwnd");
            return true;
        }

        PointerByReference pointer = new PointerByReference();
        int foregroundThreadID = user32.GetWindowThreadProcessId(user32.GetForegroundWindow(), pointer);

        PointerByReference thisPointer = new PointerByReference();
        int thisThreadID = user32.GetWindowThreadProcessId(hwnd, thisPointer);

        if (user32.AttachThreadInput(new DWORD(thisThreadID), new DWORD(foregroundThreadID), true)) {
            System.out.println("user32.AttachThreadInput(new DWORD(thisThreadID), new DWORD(foregroundThreadID), true)");
            user32.BringWindowToTop(hwnd);
            user32.SetForegroundWindow(hwnd);
            user32.AttachThreadInput(new DWORD(thisThreadID), new DWORD(foregroundThreadID), false);
            if (user32.GetForegroundWindow() == hwnd)
                return true;
        }

        HWND hwnd1 = user32.GetForegroundWindow();

        /*timeout = ctypes.c_int()
        zero = ctypes.c_int(0)*/
        PointerByReference timeout = new PointerByReference();
        PointerByReference timeoutZero = new PointerByReference();
        timeoutZero.setValue(new Pointer(0));
        System.out.println(user32.SystemParametersInfo(user32.SPI_GETFOREGROUNDLOCKTIMEOUT, 0, timeout, 0));
        System.out.println(user32.SystemParametersInfo(user32.SPI_SETFOREGROUNDLOCKTIMEOUT, 0, timeoutZero, user32.SPIF_SENDCHANGE));
        System.out.println("BringWindowToTop: " + user32.BringWindowToTop(hwnd));
        System.out.println(user32.SetWindowPos(hwnd, hwnd1, 0, 0, 0, 0, user32.SWP_SHOWWINDOW | user32.SWP_NOSIZE | user32.SWP_NOMOVE));
        System.out.println(user32.SetForegroundWindow(hwnd));
        System.out.println(user32.SystemParametersInfo(user32.SPI_SETFOREGROUNDLOCKTIMEOUT, 0, timeout, user32.SPIF_SENDCHANGE));

        System.out.println("SetFocus: " + user32.SetFocus(hwnd));
        System.out.println("SetActiveWindow: " + user32.SetActiveWindow(hwnd));

        if (user32.GetForegroundWindow() == hwnd)
            return true;

        return false;
    }

    private static void esperar() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
