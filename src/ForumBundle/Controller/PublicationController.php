<?php

namespace ForumBundle\Controller;

use ForumBundle\Entity\Publication;
use ForumBundle\Form\PublicationType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Security\Core\Security;
use Tutorial\BlogBundle\Form\PostType;

class PublicationController extends Controller
{
    public function addPubAction(Request $request)
    {
        $pub= new Publication();
        $form= $this->createForm(PublicationType::class,$pub);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            if ($pub->getImage() !== null){
                $file = $pub->getImage();
                $filename = md5(uniqid()). '.' . $file->guessExtension();
                $file->move($this->getParameter('media_directory'), $filename);
                $pub->setImage($filename);
            }
            else{
                $pub->setImage(' ');
            }
            $pub->setPublishedAt(new \DateTime("now"));
            $pub->user = $this->getUser();
            $em = $this->getDoctrine()->getManager();
            $em->persist($pub);
            $em->flush();
            return $this->redirectToRoute("list_publication");
        }
        return $this->render("@Forum/dashboard/addPub.html.twig",array('Form'=>$form->createView()));
    }
    public function addPubFrontAction(Request $request)
    {
        $pub= new Publication();
        $form= $this->createForm(PublicationType::class,$pub);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            if ($pub->getImage() !== null){
            $file = $pub->getImage();
            $filename = md5(uniqid()). '.' . $file->guessExtension();
            $file->move($this->getParameter('media_directory'), $filename);
            $pub->setImage($filename);
            }
            else{
                $pub->setImage(' ');
            }
            $pub->setPublishedAt(new \DateTime("now"));
            $pub->user = $this->getUser();
            $em = $this->getDoctrine()->getManager();
            $em->persist($pub);
            $em->flush();
            return $this->redirectToRoute("blog");
        }
        return $this->render("@Forum/front/addPub.html.twig",array('Form'=>$form->createView()));
    }

    public function listPubAction(Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $posts=$em->getRepository('ForumBundle:Publication')->findAll();
        return $this->render('@Forum/dashboard/publication.html.twig', array(
            "posts" =>$posts
        ));
    }
    public function frontBlogAction(Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $posts=$em->getRepository('ForumBundle:Publication')->findAll();
        return $this->render('@Forum/front/blog.html.twig', array(
            "posts" =>$posts
        ));
    }

    public function updatePubAction(Request $request, $id)
    {
        $em=$this->getDoctrine()->getManager();
        $p= $em->getRepository('ForumBundle:Publication')->find($id);
        $form=$this->createForm(PublicationType::class,$p);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            if ($p->getImage() !== null){
                $file = $p->getImage();
                $filename = md5(uniqid()). '.' . $file->guessExtension();
                $file->move($this->getParameter('media_directory'), $filename);
                $p->setImage($filename);
            }
            else{
                $p->setImage(' ');
            }
            $em= $this->getDoctrine()->getManager();
            $em->persist($p);
            $em->flush();
            return $this->redirectToRoute('list_publication');

        }
        return $this->render('@Forum/dashboard/updatePub.html.twig', array(
            "Form"=> $form->createView()
        ));
    }

    public function deletePubAction(Request $request)
    {
        $id = $request->get('id');
        $em= $this->getDoctrine()->getManager();
        $Post=$em->getRepository('ForumBundle:Publication')->find($id);
        $em->remove($Post);
        $em->flush();
        return $this->redirectToRoute('list_publication');
    }
    public function updatePubFrontAction(Request $request, $id)
    {
        $em=$this->getDoctrine()->getManager();
        $p= $em->getRepository('ForumBundle:Publication')->find($id);
        $form=$this->createForm(PublicationType::class,$p);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            if ($p->getImage() !== null){
                $file = $p->getImage();
                $filename = md5(uniqid()). '.' . $file->guessExtension();
                $file->move($this->getParameter('media_directory'), $filename);
                $p->setImage($filename);
            }
            else{
                $p->setImage(' ');
            }
            $em= $this->getDoctrine()->getManager();
            $em->persist($p);
            $em->flush();
            return $this->redirectToRoute('blog');

        }
        return $this->render('@Forum/front/updatePub.html.twig', array(
            "Form"=> $form->createView()
        ));
    }

    public function deletePubFrontAction(Request $request)
    {
        $id = $request->get('id');
        $em= $this->getDoctrine()->getManager();
        $Post=$em->getRepository('ForumBundle:Publication')->find($id);
        $em->remove($Post);
        $em->flush();
        return $this->redirectToRoute('blog');
    }

    public function showPubAction($id)
    {
        $em= $this->getDoctrine()->getManager();
        $p=$em->getRepository('ForumBundle:Publication')->find($id);
        return $this->render('@Forum/front/detailedpost.html.twig', array(
            'post'=>$p
        ));
    }

}
