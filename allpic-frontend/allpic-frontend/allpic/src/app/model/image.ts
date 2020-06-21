import { User } from './user';

export interface Image {
    id: number;
    token: string;
    title: string;
    uploadTime: Date;
    isPublic: boolean;
    isActive: boolean;
    uploader: User;

}
