package io.github.linwancen.plugin.comment.utils;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;

public class ResolveElementUtils {

    private ResolveElementUtils() {}

    @Nullable
    public static PsiElement resolveElementOf(PsiElement element, PsiElement psiIdentifier,
                                              int startOffset, int endOffset) {
        PsiElement newPsiElement = resolveElementForExecutable(element, startOffset, endOffset);
        if (newPsiElement != null) {
            return newPsiElement;
        }
        PsiReference psiReference = parentPsiReference(psiIdentifier, endOffset);
        if (psiReference != null) {
            return psiReference.resolve();
        }
        return null;
    }

    @Nullable
    private static PsiElement resolveElementForExecutable(PsiElement element, int startOffset, int endOffset) {
        if (element == null) {
            return null;
        }
        // method call
        PsiMethodCallExpression call =
                PsiTreeUtil.getParentOfType(element, PsiMethodCallExpression.class, false, startOffset);
        if (call != null) {
            // skip double comment when method call in new line:
            // someObject // someMethodComment
            //   .someMethod(); // someMethodComment
            if ((call.getNode().getStartOffset() + call.getNode().getTextLength()) > endOffset) {
                return null;
            }
            try {
                return call.resolveMethod();
            } catch (Exception e) {
                return null;
            }
        }
        // new and Class should same line
        PsiNewExpression newExp = PsiTreeUtil.getParentOfType(element, PsiNewExpression.class, false, startOffset);
        if (newExp != null) {
            PsiMethod psiMethod = newExp.resolveMethod();
            if (psiMethod != null) {
                return psiMethod;
            }
            PsiJavaCodeReferenceElement classReference = newExp.getClassReference();
            if (classReference != null) {
                return classReference.resolve();
            }
        }
        return null;
    }

    /**
     * ::/class/field
     */
    @Nullable
    private static PsiReference parentPsiReference(PsiElement element, int endOffset) {
        if (element == null) {
            return null;
        }
        PsiElement parent;
        while ((parent = element.getParent()) instanceof PsiReference) {
            if (parent.getTextRange().getEndOffset() > endOffset) {
                break;
            }
            element = parent;
        }
        if (element instanceof PsiReference) {
            return (PsiReference) element;
        }
        return null;
    }
}
