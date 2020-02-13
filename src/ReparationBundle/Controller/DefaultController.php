<?php

namespace ReparationBundle\Controller;

use ReparationBundle\Entity\Piecesdefectueuses;
use ReparationBundle\Entity\Reparation;
use ReparationBundle\Form\PiecesdefectueusesType;
use ReparationBundle\Form\ReparationType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use UserBundle\Entity\Repairer;

class DefaultController extends Controller
{
    public function reparationAction()
    {
        return $this->render('@Reparation/dashboard/add_defective.html.twig');
    }
    public function promotionAction()
    {
        return $this->render('@Reparation/dashboard/promotion.html.twig');
    }
    public function add_defectiveAction(Request $request)
    {
        $piece = new Piecesdefectueuses();
        $form = $this->createForm(PiecesdefectueusesType::class, $piece);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $user = $this->getUser();
            $piece->setUser($user);
            $piece->setReserved(false);
            $file = $piece->getImage();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('photos_directory'), $fileName);
            $piece->setImage($fileName);
            $em->persist($piece);
            $em->flush();
        }


        return $this->render('@Reparation/front/add_defective.html.twig', [
            'form' => $form->createView()
        ]);



    }

    public function list_defectiveAction()
    {
        $pieces = $this->getDoctrine()->getRepository('ReparationBundle:Piecesdefectueuses')->findDefective();
        return $this->render('@Reparation/front/list_defective.html.twig', [
            'pieces' => $pieces,
        ]);
    }

    public function repareAction($idP,$idR,Request $request)
    {

        $reparation = new Reparation();
        $form = $this->createForm(ReparationType::class, $reparation);
        $piece = $this->getDoctrine()->getRepository('ReparationBundle:Piecesdefectueuses')->find($idP);
        $repairer = $this->getDoctrine()->getRepository('UserBundle:User')->find($idR);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $piece->setReserved(true);
            $reparation->setPiecedefectueuse($piece);
            $reparation->setReparateur($repairer);
            $em->persist($reparation);
            $em->flush();
        }


        return $this->render('@Reparation/front/repaire.html.twig', [
            'form' => $form->createView(),
        ]);
    }
    public function frontpromotionAction()
    {
        return $this->render('@Reparation/front/promotion.html.twig');
    }
}
