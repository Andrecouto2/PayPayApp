package br.com.andrecouto.paypay.util;

import android.annotation.SuppressLint;
import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.animation.Animation;

import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.fragment.BaseFragment;

public class FragmentUtils {

    private FragmentUtils() {
        //unused
    }

    @SuppressLint("CommitTransaction")
    private static FragmentTransaction ensureTransaction(final FragmentManager fragmentManager) {
        return fragmentManager.beginTransaction();
    }

    public static Fragment getFragment(final FragmentManager fragmentManager, final String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

    public static Fragment getFragment(final FragmentManager fragmentManager, final int id) {
        return fragmentManager.findFragmentById(id);
    }

    private static void attachFragment(final FragmentTransaction fragmentTransaction,
                                       final int content, final BaseFragment fragment) {
        if (fragment != null) {
            if (fragment.isDetached()) {
                fragmentTransaction.attach(fragment);
            } else if (!fragment.isAdded()) {
                fragmentTransaction.add(content, fragment, fragment.tag());
            }
            fragmentTransaction.commit();
        }
    }

    public static void attachFragmentWithAnimation(final FragmentManager fragmentManager,
                                                   final int containerId,
                                                   final BaseFragment fragment,
                                                   boolean addToBackStack) {

        FragmentTransaction fragmentTransaction = ensureTransaction(fragmentManager);

        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in,
                R.anim.fade_out);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.tag());
        }
        FragmentUtils.attachFragment(fragmentTransaction, containerId, fragment);
    }

    public static void removeFragment(final FragmentManager fragmentManager,
                                      final Fragment fragment, final boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = ensureTransaction(fragmentManager);
        fragmentTransaction.remove(fragment);
        commitTransactions(fragmentTransaction, addToBackStack);
    }

    public static void replaceFragmentWithAnimation(final FragmentManager fragmentManager,
                                                    final BaseFragment fragment,
                                                    final int containerToReplace,
                                                    final boolean addToBackStack,
                                                    @AnimRes final int animationEnter,
                                                    @AnimRes final int animationExit) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.tag());
        }
        fragmentTransaction.setCustomAnimations(animationEnter, animationExit);
        setFragmentsVisibleHintWithAnimation(fragmentManager, fragment, containerToReplace);

        fragmentTransaction.replace(containerToReplace, fragment).commit();
    }

    public static void replaceFragmentWithManyAnimations(final FragmentManager fragmentManager,
                                                         final BaseFragment fragment,
                                                         final int containerToReplace,
                                                         final boolean addToBackStack,
                                                         @AnimRes final int animationEnter,
                                                         @AnimRes final int animationExit,
                                                         @AnimRes final int animationPopEnter,
                                                         @AnimRes final int animationPopExit) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.tag());
        }
        fragmentTransaction.setCustomAnimations(animationEnter, animationExit, animationPopEnter,
                animationPopExit);
        setFragmentsVisibleHintWithAnimation(fragmentManager, fragment, containerToReplace);

        fragmentTransaction.replace(containerToReplace, fragment, fragment.tag()).commit();
    }

    public static void replaceFragmentWithManyAnimations(final FragmentManager fragmentManager,
                                                         final Fragment fragment,
                                                         final int containerToReplace,
                                                         final boolean addToBackStack,
                                                         final String tag,
                                                         @AnimRes final int animationEnter,
                                                         @AnimRes final int animationExit,
                                                         @AnimRes final int animationPopEnter,
                                                         @AnimRes final int animationPopExit) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.setCustomAnimations(animationEnter, animationExit, animationPopEnter,
                animationPopExit);

        fragmentTransaction.replace(containerToReplace, fragment, tag).commit();
    }

    public static void replaceFragment(final FragmentManager fragmentManager,
                                       final BaseFragment fragment, final int containerToReplace,
                                       final boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = ensureTransaction(fragmentManager);
        fragmentTransaction.replace(containerToReplace, fragment, fragment.tag());
        setFragmentsVisibleHintWithAnimation(fragmentManager, fragment, containerToReplace);

        commitTransactions(fragmentTransaction, addToBackStack);
    }

    private static void setFragmentsVisibleHintWithAnimation(final FragmentManager fragmentManager,
                                                             final BaseFragment fragment,
                                                             final int containerToReplace) {
        fragment.setTransitionAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //unused
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setFragmentsVisibleHint(fragmentManager, fragment, containerToReplace);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //unused
            }
        });
    }

    private static void setFragmentsVisibleHint(FragmentManager fragmentManager, Fragment fragment,
                                                int containerToReplace) {
        if (fragmentManager != null) {
            Fragment currentFragment = fragmentManager.findFragmentById(containerToReplace);
            if (currentFragment != null) {
                currentFragment.setUserVisibleHint(false);
            }
            fragment.setUserVisibleHint(true);
        }
    }

    public static void replaceFragment(final FragmentManager fragmentManager,
                                       final Fragment fragment, final int containerToReplace,
                                       final boolean addToBackStack, String tag) {
        FragmentTransaction fragmentTransaction = ensureTransaction(fragmentManager);
        fragmentTransaction.replace(containerToReplace, fragment, tag);
        commitTransactions(fragmentTransaction, addToBackStack);

        setFragmentsVisibleHint(fragmentManager, fragment, containerToReplace);
    }

    private static void commitTransactions(final FragmentTransaction fragmentTransaction,
                                           final boolean addToBackStack) {
        if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Fragment> T findFragment(final FragmentManager fragmentManager,
                                                      final String tag) {
        return (T) fragmentManager.findFragmentByTag(tag);
    }

    public static void backToFirstFragment(final FragmentManager fragmentManager) {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            if (!fragmentManager.isDestroyed()) {
                fragmentManager.popBackStackImmediate(first.getId(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    public static Fragment getCurrentFragment(final FragmentManager fragmentManager) {
        final int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            String fragmentTag =
                    fragmentManager.getBackStackEntryAt(backStackEntryCount - 1).getName();
            return fragmentManager
                    .findFragmentByTag(fragmentTag);
        }
        return null;
    }


}
