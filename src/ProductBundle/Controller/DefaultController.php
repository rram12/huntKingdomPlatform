<?php

namespace ProductBundle\Controller;

use ProductBundle\Entity\Promotion;
use ProductBundle\Form\PromotionType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;

class DefaultController extends Controller
{
    public function productsAction()
    {
        return $this->render('@Product/dashboard/products.html.twig');
    }
    public function frontshopAction()
    {
        return $this->render('@Product/front/shop.html.twig');
    }
    public function frontshopsingleAction()
    {
        return $this->render('@Product/front/shop-single.html.twig');
    }

    public function productListAction()
    {
        $produits = $this->getDoctrine()->getRepository('ProductBundle:Produit')->findAll();
        return $this->render('@Product/dashboard/products.html.twig', [
            'produits' => $produits,
        ]);
    }
    public function affectPromotionAction($id,Request $request)
    {
        $promotion = new Promotion();
        $date = new \DateTime();
        $form = $this->createForm(PromotionType::class, $promotion);
        $produit = $this->getDoctrine()->getRepository('ProductBundle:Produit')->find($id);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $promotion->setActive(true);
            $promotion->setDateDebut($date);
            $produit->setPromotion($promotion);
            $em->persist($promotion);
            $em->persist($produit);
            $em->flush();
        }


        return $this->render('@Product/dashboard/promotions.html.twig', [
            'form' => $form->createView(),
        ]);
    }
    public function listPromotionAction()
    {
        $promotions = $this->getDoctrine()->getRepository('ProductBundle:Promotion')->findAll();
        return $this->render('@Product/dashboard/promotionslist.html.twig', [
            'promotions' => $promotions,
        ]);
    }
    public function deletePromotionAction($id)
    {
        $promotion = $this->getDoctrine()->getRepository('ProductBundle:Promotion')->find($id);
        $produit = $this->getDoctrine()->getRepository('ProductBundle:Produit')->findProduit($id);
        if ($produit) {
            $produit->setPromotion(null);
        }

        $em = $this->getDoctrine()->getManager();
        $em->persist($produit);
        $em->remove($promotion);
        $em->flush();
        return $this->redirectToRoute('list_promotion');
    }

    public function modifierPromotionAction($id, Request $request)
    {
        $promotion = $this->getDoctrine()->getRepository('ProductBundle:Promotion')->find($id);
        $form = $this->createForm(PromotionType::class, $promotion);
        $form->remove('confirm');
        $form->add('Modify', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $promotion = $form->getData();
            $em = $this->getDoctrine()->getManager();
            $em->persist($promotion);
            $em->flush();

            return $this->redirectToRoute('list_promotion');
        }

        return $this->render('@Product/dashboard/promotions.html.twig', [
            'form' => $form->createView()
        ]);

    }
}
