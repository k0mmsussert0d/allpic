export interface Message {
  type: 'success' | 'failure' | 'warning',
  text?: string
}

export interface APIResponse<T> {
  message?: Message,
  response?: T
}

export interface UserDetails {
  role: string,
  username: string,
}

export interface UserDTO {
  email: string,
  id: number,
  isActive: boolean,
  registerTime: string,
  role: {
    id: number,
    roleName: string
  },
  username: string
}

export interface ImageDTO {
  id: number,
  isActive: boolean,
  isPublic: boolean,
  title: string,
  token: string,
  uploadTime: string,
  uploader: UserDTO
}

export interface ImagePreviewDetails {
  token: string,
  title: string
}

export interface CommentDTO {
  author: string,
  id: number,
  isPublic: boolean,
  message: string,
  timeAdded: string
}