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
            $fileName = md5(uniqid()) . '.' . $file->guessExtension();
            $file->move($this->getParameter('photos_directory'), $fileName);
            $piece->setImage($fileName);
            $em->persist($piece);
            $em->flush();
            return $this->redirectToRoute('main_front');


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

    public function repareAction($idP, $idR, Request $request)
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
            return $this->redirectToRoute('reparation_front_list_defective');

        }


        return $this->render('@Reparation/front/repaire.html.twig', [
            'form' => $form->createView(),
            'idP' => $idP,
            'idR' => $idR
        ]);
    }

    public function frontpromotionAction()
    {
        return $this->render('@Reparation/front/promotion.html.twig');
    }


    public function list_you_defectiveAction($idUser)
    {


        $pieces = $this->getDoctrine()->getRepository('ReparationBundle:Piecesdefectueuses')->find_my_Defective($idUser);
        $date = new \DateTime();
        $reparations_ready =  $this->getDoctrine()->getRepository('ReparationBundle:Reparation')->find_my_ready($idUser,$date);
        $em = $this->getDoctrine()->getManager();
        foreach ($reparations_ready as $reparation_ready){
            $piece_ready = $reparation_ready->getPiecedefectueuse();
            $piece_ready->setEtat(true);
            $em->persist($piece_ready);
        }
        $em->flush();
        return $this->render('@Reparation/front/list_my_defective.html.twig', [
            'pieces' => $pieces,
        ]);
    }

    public function show_progressAction($idP)
    {
        $reparation = $this->getDoctrine()->getRepository('ReparationBundle:Reparation')->find_my_reparation($idP);
        $piece = $this->getDoctrine()->getRepository('ReparationBundle:Piecesdefectueuses')->find($idP);

        $current_date = new \DateTime();
        $date_fin = $reparation->getDateFin();
        $timediff = $current_date->diff($date_fin);
        $years = $timediff->y;
        $months = $timediff->m;
        $days = $timediff->d;
        $seconds = $timediff->s;
        $minutes = $timediff->i;
        $hours = $timediff->h;
        return $this->render('@Reparation/front/show_progress.html.twig', [
            'reparation' => $reparation,
            'piece' => $piece,
            'years' => $years,
            'months' => $months,
            'days' => $days,
            'seconds' => $seconds,
            'minutes' => $minutes,
            'hours' => $hours
        ]);
    }
    public function show_readyAction($idP)
    {
        $reparation = $this->getDoctrine()->getRepository('ReparationBundle:Reparation')->find_my_reparation($idP);
        $piece = $this->getDoctrine()->getRepository('ReparationBundle:Piecesdefectueuses')->find($idP);

        return $this->render('@Reparation/front/show_ready.html.twig', [
            'reparation' => $reparation,
            'piece' => $piece,
        ]);
    }

    public function show_repairer_profileAction($idR)
    {
        $user = $this->getDoctrine()->getRepository('UserBundle:User')->find($idR);
        return $this->render('@Reparation/front/show_repairer_profile.html.twig', [
            'user' => $user,
        ]);
    }


    public function finish_reparationAction($idR,$idP)
    {
        $piece = $this->getDoctrine()->getRepository('ReparationBundle:Piecesdefectueuses')->find($idP);
        $reparation = $this->getDoctrine()->getRepository('ReparationBundle:Reparation')->find($idR);
        if ($reparation) {
            $reparation->setPiecedefectueuse(null);
        }
        $em = $this->getDoctrine()->getManager();
        $em->remove($piece);
        $em->remove($reparation);
        $em->flush();
        $id = $this->getUser()->getId();
        return $this->redirectToRoute('reparation_front_list_your_defective',[
            'idUser' => $id
        ]);
    }
}
